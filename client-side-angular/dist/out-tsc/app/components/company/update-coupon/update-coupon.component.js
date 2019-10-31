import * as tslib_1 from "tslib";
import { Component } from '@angular/core';
import { CouponCategory } from 'src/app/shared/models/couponCategory';
let UpdateCouponComponent = class UpdateCouponComponent {
    constructor(companyService, httpClient) {
        this.companyService = companyService;
        this.httpClient = httpClient;
        this.wasChange = false;
        this.couponExpired = false;
        this.backwardDates = false;
    }
    ngOnInit() {
        var categories = Object.keys(CouponCategory);
        this.categories = categories.slice(categories.length / 2);
        this.companyService.getAllCoupons(); //for dropdown
        this.couponId = this.companyService.allCoupons[0].id; //initialize dropdown
    }
    fillCouponDetails() {
        this.httpClient.get(`${this.companyService.baseUrl}coupon/${this.couponId}`, { withCredentials: true })
            .subscribe(res => { this.couponToUpdate = res; }, err => this.companyService.errorMessage = err.error.messages);
    }
    submitChanges() {
        this.companyService.updateCoupon(this.couponToUpdate);
    }
    enableSubmit() {
        this.wasChange = true;
    }
    checkDates() {
    }
};
UpdateCouponComponent = tslib_1.__decorate([
    Component({
        selector: 'app-update-coupon',
        templateUrl: './update-coupon.component.html',
        styleUrls: ['./update-coupon.component.css']
    })
], UpdateCouponComponent);
export { UpdateCouponComponent };
//# sourceMappingURL=update-coupon.component.js.map