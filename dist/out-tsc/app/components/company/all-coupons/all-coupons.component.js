import * as tslib_1 from "tslib";
import { Component } from '@angular/core';
import { CouponCategory } from 'src/app/shared/models/couponCategory';
let AllCouponsComponent = class AllCouponsComponent {
    constructor(companyService, loginService, httpClient) {
        this.companyService = companyService;
        this.loginService = loginService;
        this.httpClient = httpClient;
        this.CouponCategory = CouponCategory; //do we need this?
    }
    ngOnInit() {
        //initializing categories array with categories.
        var categories = Object.keys(CouponCategory);
        this.categories = categories.slice(categories.length / 2);
        this.companyService.getAllCoupons();
    }
    selectByPrice() {
        this.companyService.selectByPrice(this.byPrice);
    }
    selectByCategory() {
        this.companyService.selectByCategory(this.byCategory);
    }
    selectByDate() {
        this.companyService.selectByDate(this.byDate);
    }
};
AllCouponsComponent = tslib_1.__decorate([
    Component({
        selector: 'app-all-coupons',
        templateUrl: './all-coupons.component.html',
        styleUrls: ['./all-coupons.component.css']
    })
], AllCouponsComponent);
export { AllCouponsComponent };
//# sourceMappingURL=all-coupons.component.js.map