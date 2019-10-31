import * as tslib_1 from "tslib";
import { Component } from '@angular/core';
let SideBarComponent = class SideBarComponent {
    //see how to make div's use the enum and not number. 
    constructor(loginService) {
        this.loginService = loginService;
    }
    ngOnInit() {
    }
};
SideBarComponent = tslib_1.__decorate([
    Component({
        selector: 'app-side-bar',
        templateUrl: './side-bar.component.html',
        styleUrls: ['./side-bar.component.css']
    })
], SideBarComponent);
export { SideBarComponent };
//# sourceMappingURL=side-bar.component.js.map