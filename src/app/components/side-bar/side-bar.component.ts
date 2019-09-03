import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/shared/services/login.service';

@Component({
    selector: 'app-side-bar',
    templateUrl: './side-bar.component.html',
    styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit {

    constructor(public loginService: LoginService) {
    }

    ngOnInit() {
    }

}
