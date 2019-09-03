import { Component, OnInit } from '@angular/core';
import { Login } from '../../shared/models/login';
import { LoginService } from '../../shared/services/login.service';
import { LoginType } from '../../shared/models/loginType';
import { Router } from '@angular/router';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    types: string[];

    //Contains the login details from the form
    public login: Login = new Login();
    constructor(public loginService: LoginService) { }

    ngOnInit() {
        //Retrieving types from loginType enum
        var typeArray = Object.keys(LoginType);

        //Removing 'keys' value at the end of enum array
        this.types=typeArray.slice(0, typeArray.length-1);

        //Initialize the dropdown
        this.login.type=this.types[0];
    }

    send(): void {
        //sends the login details.  
        this.loginService.checkLogin(this.login);
    }


}
