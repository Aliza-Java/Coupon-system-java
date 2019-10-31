import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
let CustomerService = class CustomerService {
    constructor(httpClient, loginService) {
        this.httpClient = httpClient;
        this.loginService = loginService;
        this.baseUrl = "http://localhost:8080/CouponSystem/sec/customer/";
        //after do login - i wonder if login details could be put in through the login.  i think it should to avoid duplicates.
        this.httpClient.get(`${this.baseUrl}details/${this.loginService.user.id}`)
            .subscribe(res => this.currentCustomer = res, err => this.errorMessage = err);
        //fill in systemcoupons for purchase-coupon
        this.httpClient.get(`${this.baseUrl}allSystemCoupons`)
            .subscribe(res => this.allSystemCoupons = res, err => this.errorMessage = err);
    }
    resetMessages() {
        this.errorMessage = "";
        this.resultMessage = "";
    }
    purchase(couponId) {
        if (confirm(`Are you sure you want to purchase coupon ${couponId}?`)) {
            this.httpClient.post(`${this.baseUrl}purchase/${couponId}`, null)
                //not sure why alert doesn't work.
                .subscribe(res => alert(res), err => this.errorMessage = err);
        }
    }
};
CustomerService = tslib_1.__decorate([
    Injectable({
        providedIn: 'root'
    })
], CustomerService);
export { CustomerService };
//# sourceMappingURL=customer.service.js.map