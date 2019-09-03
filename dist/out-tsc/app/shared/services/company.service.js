import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
let CompanyService = class CompanyService {
    constructor(httpClient, loginService) {
        //adding these into constructor since this data is frequently needed.
        this.httpClient = httpClient;
        this.loginService = loginService;
        this.baseUrl = "http://localhost:8080/CouponSystem/sec/company/";
        this.errorMessage = "";
        this.httpClient.get(this.baseUrl, { withCredentials: true })
            .subscribe(res => this.allCoupons = res, err => alert(err.error.messages));
        //after do login - i wonder if login details could be put in through the login.  i think it should to avoid duplicates.
        this.httpClient.get(`${this.baseUrl}details/${this.loginService.user.id}`, { withCredentials: true })
            .subscribe(res => this.currentCompany = res, err => alert(err.error.messages));
    }
    createCoupon(newCoupon) {
        this.httpClient.post(`${this.baseUrl}createcoupon`, newCoupon, { withCredentials: true })
            .subscribe(res => this.resultMessage = `Coupon with id ${res.id} created successfully!`, err => alert(err.error.messages));
    }
    //will load company into currentCompany, e.g. on start of company home
    getCompany() {
        this.httpClient.get(`${this.baseUrl}details/${this.loginService.user.id}`, { withCredentials: true })
            .subscribe(res => this.currentCompany = res, err => this.errorMessage = err.error.messages);
    }
    updateCoupon(couponToUpdate) {
        this.httpClient.put(`${this.baseUrl}updatecoupon${couponToUpdate.id}`, couponToUpdate, { withCredentials: true })
            .subscribe(res => alert(`Coupon ${couponToUpdate.id} updated successfully!`), err => alert(err.error.messages));
    }
    resetMessages() {
        this.resultMessage = "";
        this.errorMessage = "";
        this.couponToGet = null;
    }
    getAllCoupons() {
        // MUST REMOVE KEYS VALUE AT THE END OF THE ENUM DROPDOWN.
        //fill in allCoupons as full data, and selectedCoupons since all-coupons.html draws on it in ngFor.
        this.httpClient.get(this.baseUrl, { withCredentials: true })
            .subscribe(res => { this.allCoupons = res; this.selectedCoupons = res; }, err => this.errorMessage = err.error.messages);
    }
    deleteCoupon(id) {
        if (confirm(`Are you sure you want to delete coupon ${id}?`)) {
            this.httpClient.delete(`${this.baseUrl}removecoupon/${id}`, { withCredentials: true })
                .subscribe(res => {
                alert(`Coupon ${id} deleted successfully.`);
                this.getAllCoupons(); //update table with updated list of existing coupons
            }, err => {
                alert("Unable to delete.  " + err.error.messages);
                this.getAllCoupons(); //the dropdown should be updated with existing coupons
            });
        }
        else {
            //allow user to change his mind.
        }
    }
    findCouponNow(id) {
        this.httpClient.get(`${this.baseUrl}coupon/${id}`, { withCredentials: true })
            .subscribe(res => this.couponToGet = res, err => alert(err.error.messages));
    }
    selectByPrice(price) {
        this.selectedCoupons = [];
        this.httpClient.get(this.baseUrl + `byPrice/${price}`, { withCredentials: true })
            .subscribe(res => this.selectedCoupons = res, err => alert(err.error.messages));
    }
    selectByCategory(category) {
        this.selectedCoupons = [];
        this.httpClient.get(this.baseUrl + `category/${category}`, { withCredentials: true })
            .subscribe(res => this.selectedCoupons = res, err => alert(err.error.messages));
    }
    selectByDate(date) {
        this.selectedCoupons = [];
        this.httpClient.get(this.baseUrl + `beforeDate/${date}`, { withCredentials: true })
            .subscribe(res => this.selectedCoupons = res, err => alert(err.error.messages));
    }
};
CompanyService = tslib_1.__decorate([
    Injectable({
        providedIn: 'root'
    })
], CompanyService);
export { CompanyService };
//# sourceMappingURL=company.service.js.map