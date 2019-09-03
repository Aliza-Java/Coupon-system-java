import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
import { User } from '../models/user';
let LoginService = class LoginService {
    constructor(httpClient, router) {
        this.httpClient = httpClient;
        this.router = router;
        //to change according to login.
        this.isLoggedIn = false;
        this.baseUrl = "http://localhost:8080/CouponSystem/";
        //initiating so that sidebar component can rely on this variable.
        this.user = new User();
        //in case user refreshed or reopened browser 
        this.isLoggedIn = localStorage.getItem("isLoggedIn") === "true";
    }
    checkLogin(login) {
        //login types on server are stored in capital letters.
        login.type = login.type.toUpperCase();
        this.httpClient.post(`${this.baseUrl}log/in`, login, { withCredentials: true })
            .subscribe((res) => {
            localStorage.setItem("isLoggedIn", "true");
            this.isLoggedIn = true;
            this.user = res;
            this.username = login.username;
            this.router.navigate([`${this.user.type.toLowerCase()}/home`]);
        }, (err) => alert('There was a problem logging in. ' + err.error.messages));
    }
    logout() {
        if (confirm("Are you sure you want to log out?")) { //giving user a chance to regret it
            this.isLoggedIn = false;
            localStorage.removeItem("isLoggedIn");
            this.user.type = null; //side bar will return to blank
            this.httpClient.post(`${this.baseUrl}log/out`, null, { withCredentials: true })
                .subscribe(res => this.router.navigate([`/login`]), err => alert(err));
        } //end if
    }
};
LoginService = tslib_1.__decorate([
    Injectable({
        providedIn: 'root'
    })
], LoginService);
export { LoginService };
//# sourceMappingURL=login.service.js.map