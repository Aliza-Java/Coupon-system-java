import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Company } from '../models/company';
import { Coupon } from '../models/coupon';
import { LoginService } from './login.service';
import { CouponCategory } from '../models/couponCategory';

@Injectable({
    providedIn: 'root'
})
export class CompanyService {

    baseUrl: string = "http://localhost:8080/CouponSystem/sec/company/";
    allCoupons: Coupon[];
    selectedCoupons: Coupon[];
    couponToGet: Coupon;
    newCoupon: Coupon;
    currentCompany: Company;
    resultMessage: string;
    errorMessage: string = "";
    emptyList: boolean;
    categories: string[];

    public constructor(public httpClient: HttpClient, public loginService: LoginService) {

        //initializing categories array with categories.
        var categories = Object.keys(CouponCategory);

        //selecting category values from category array 
        var from = Math.trunc(categories.length / 2);
        var to = from * 2;
        this.categories = categories.slice(from, to);

        this.emptyList = false;
        this.httpClient.get<Company>(`${this.baseUrl}details/${this.loginService.user.id}`, { withCredentials: true })
            .subscribe(res => this.currentCompany = res, err => alert(err.error.messages));
    }

    resetMessages() {//start anew every time a user clicks a router link.
        this.resultMessage = "";
        this.errorMessage = "";
        this.couponToGet = null;
        this.emptyList = false;
    }

    //will load company into currentCompany, e.g. on start of company home
    getCompany(): void {
        this.httpClient.get<Company>(`${this.baseUrl}details/${this.loginService.user.id}`, { withCredentials: true })
            .subscribe(res => this.currentCompany = res, err => this.errorMessage = err.error.messages);
    }

    public getAllCoupons() {

        //fill in allCoupons as full data, and selectedCoupons since all-coupons.html draws on it in *ngFor.
        this.httpClient.get<Coupon[]>(this.baseUrl, { withCredentials: true })
            .subscribe(res => {
                this.allCoupons = res;
                this.selectedCoupons = res;
            },
                err => this.errorMessage = err.error.messages);
    }

    createCoupon(newCoupon: Coupon) {
        this.httpClient.post<Coupon>(`${this.baseUrl}createcoupon`, newCoupon, { withCredentials: true })
            .subscribe(res => this.resultMessage = `Coupon with id ${res.id} created successfully!`, err => alert(err.error.messages));
    }

    public findCouponNow(id: number): void {
        this.httpClient.get<Coupon>(`${this.baseUrl}coupon/${id}`, { withCredentials: true })
            .subscribe(res => this.couponToGet = res, err => alert(err.error.messages));
    }

    updateCoupon(couponToUpdate: Coupon) {
        this.httpClient.put(`${this.baseUrl}updatecoupon/${couponToUpdate.id}`, couponToUpdate, { withCredentials: true })
            .subscribe(res => alert(`Coupon ${couponToUpdate.id} updated successfully!`), err => this.errorMessage = err.error.messages);
    }

    public deleteCoupon(id: number): void {
        if (confirm(`Are you sure you want to delete coupon ${id}?`)) {
            this.httpClient.delete(`${this.baseUrl}removecoupon/${id}`, { withCredentials: true })
                .subscribe(res => {
                    alert(`Coupon ${id} deleted successfully.`);
                    this.getAllCoupons(); //update table with updated list of existing coupons

                }, err => {
                    alert("Unable to delete.  " + err.error.messages);
                    this.getAllCoupons(); //the dropdown should still be updated with existing coupons
                });
        } else {
            //allow user to change his mind.
        }
    }



    selectByPrice(price: number): void {
        this.selectedCoupons = [];
        this.httpClient.get<Coupon[]>(this.baseUrl + `byPrice/${price}`, { withCredentials: true })
            .subscribe(res => this.selectedCoupons = res,
                err => alert(err.error.messages));
    }

    selectByCategory(category: CouponCategory): void {
        this.selectedCoupons = [];
        this.httpClient.get<Coupon[]>(this.baseUrl + `category/${category}`, { withCredentials: true })
            .subscribe(res => this.selectedCoupons = res,
                err => alert(err.error.messages));
    }

    selectByDate(date: string): void {

        this.selectedCoupons = [];
        this.httpClient.get<Coupon[]>(this.baseUrl + `beforeDate/${date}`, { withCredentials: true })
            .subscribe(res => this.selectedCoupons = res,
                err => alert(err.error.messages));
    }
}