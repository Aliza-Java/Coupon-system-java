import * as tslib_1 from "tslib";
import { Component } from '@angular/core';
import { CouponCategory } from 'src/app/shared/models/couponCategory';
let CustomerCouponsComponent = class CustomerCouponsComponent {
    constructor(httpClient, customerService) {
        this.httpClient = httpClient;
        this.customerService = customerService;
        this.httpClient.get(this.customerService.baseUrl)
            //enter all coupons into selectedCoupons also, as the html shows according to selectedCoupons.
            .subscribe(res => this.selectedCoupons = this.customerCoupons = res, err => this.errorString = err);
        //initializing categories array with categories.
        var categories = Object.keys(CouponCategory);
        this.categories = categories.slice(categories.length / 2);
    }
    ngOnInit() {
    }
    selectByCategory() {
        if (this.byCategory) {
            this.httpClient.get(this.customerService.baseUrl + `byCategory/${this.byCategory}`)
                .subscribe(res => this.selectedCoupons = res, err => this.errorString = err);
        }
        else {
            alert("If you want to select by category, you must choose one.");
        }
    }
    selectAll() {
        this.selectedCoupons = this.customerCoupons;
    }
    selectByPrice() {
        if (this.byPrice) {
            this.httpClient.get(this.customerService.baseUrl + `byPrice/${this.byPrice}`)
                .subscribe(res => this.selectedCoupons = res, err => this.errorString = err);
        }
        else {
            alert("If you want to select by price, you must enter a price.");
        }
    }
};
CustomerCouponsComponent = tslib_1.__decorate([
    Component({
        selector: 'app-customer-coupons',
        templateUrl: './customer-coupons.component.html',
        styleUrls: ['./customer-coupons.component.css']
    })
], CustomerCouponsComponent);
export { CustomerCouponsComponent };
//# sourceMappingURL=customer-coupons.component.js.map