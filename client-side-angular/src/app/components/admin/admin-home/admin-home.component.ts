import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/shared/services/login.service';
import { AdminService } from 'src/app/shared/services/admin.service';

@Component({
    selector: 'app-admin-home',
    templateUrl: './admin-home.component.html',
    styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {

    public name: string;
    public todayDate: Date;

    constructor(private loginService: LoginService, private adminService: AdminService) { }

    ngOnInit() {
        this.todayDate = new Date();
    }

}
