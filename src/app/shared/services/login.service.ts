import { Injectable } from '@angular/core';
import { Login } from '../models/login';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';
import { Router } from '@angular/router';


@Injectable({
    providedIn: 'root'
})
export class LoginService {
    
    isLoggedIn: boolean = false;
    baseUrl = "http://localhost:8080/CouponSystem/";

    user: User;
    username: string;

    constructor(private httpClient: HttpClient, private router: Router) {
        //initiating so that sidebar component can rely on this variable.
        this.user = new User();

        //in case user refreshed or reopened browser 
        this.isLoggedIn = localStorage.getItem("isLoggedIn") === "true";
    }

    checkLogin(login: Login) {
        //login types on server are stored in capital letters.
        login.type = login.type.toUpperCase();
        this.httpClient.post<User>(`${this.baseUrl}log/in`, login, { withCredentials: true })
            .subscribe((res) => {
                localStorage.setItem("isLoggedIn", "true");
                this.isLoggedIn = true;
                this.user = res;
                this.username = login.username;
                this.router.navigate([`${this.user.type.toLowerCase()}/home`]);

            },
                (err) => {
                    if(err.error.messages){
                    alert(err.error.messages); 
                    }
                    else{
                        alert('There was a problem logging in.');
                    }
                });
    }

    logout() {
        if (confirm("Are you sure you want to log out?")) { //giving user a chance to regret it
            this.isLoggedIn = false;
            localStorage.removeItem("isLoggedIn");

            this.user.type = null; //side bar will return to blank

            this.httpClient.post(`${this.baseUrl}log/out`, null, { withCredentials: true })
                .subscribe(res =>
                    this.router.navigate([`/login`]), err => alert(err.error.messages));
        } //end if
    }
}
