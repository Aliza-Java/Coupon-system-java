import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Customer } from '../models/customer';
import { LoginService } from './login.service';
import { Coupon } from '../models/coupon';
import { CouponCategory } from '../models/couponCategory';

@Injectable({
    providedIn: 'root'
})
export class CustomerService {

    baseUrl: string = "http://localhost:8080/CouponSystem/sec/customer/";
    currentCustomer: Customer;
    customerCoupons: Coupon[];
    selectedCoupons: Coupon[];
    errorMessage: string;
    availableCoupons: Coupon[];
    resultMessage: string;
    categories: string[];

    constructor(private httpClient: HttpClient, private loginService: LoginService) {

        //initializing categories array with categories.
        var categories = Object.keys(CouponCategory);

        //selecting category values from category array 
        var from = Math.trunc(categories.length / 2);
        var to = from * 2;
        this.categories = categories.slice(from, to);

        this.errorMessage = "";
        this.resultMessage = "";
    }

    resetMessages() {
        this.errorMessage = "";
        this.resultMessage = "";
    }

    getCustomerDetails(): void {
        this.httpClient.get<Customer>(`${this.baseUrl}details/`, { withCredentials: true })
            .subscribe(res => this.currentCustomer = res, err => this.errorMessage = err.error.messages);
    }

    getAllMyCoupons() {
        this.httpClient.get<Coupon[]>(this.baseUrl, { withCredentials: true })
            .subscribe(res => { this.customerCoupons = res; this.selectedCoupons = res }, err => this.errorMessage = `We could not retrieve your coupons. ${err.error.messages}`);
    }

    getAvailableCoupons(): void {
        this.httpClient.get<Coupon[]>(`${this.baseUrl}availablecoupons`, { withCredentials: true })
            .subscribe(res => this.availableCoupons = res, err => this.errorMessage = `We could not retrieve the list of coupons. ${err.error.messages}`);
    }

    purchase(couponId: number) {
        this.resultMessage = ""; //clear up if user purchased before.
        if (confirm(`Are you sure you want to purchase coupon ${couponId}?`)) {
            this.httpClient.post(`${this.baseUrl}purchase/${couponId}`, null, { withCredentials: true, responseType: 'json' })
                .subscribe(res => {
                    this.resultMessage = `Coupon of id ${couponId} purchased successfully.`;
                    this.getAvailableCoupons();
                },
                    err => alert(err.error.messages));
        }
    }


    selectByPrice(price: number) {
        this.httpClient.get<Coupon[]>(this.baseUrl + `byprice/${price}`, { withCredentials: true })
            .subscribe(res => this.selectedCoupons = res, err => alert(err.error.messages));
    }

    selectByCategory(category: CouponCategory) {
        this.httpClient.get<Coupon[]>(this.baseUrl + `bycategory/${category}`, { withCredentials: true })
            .subscribe(res => this.selectedCoupons = res, err => alert(err.error.messages));
    }
}
