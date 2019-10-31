import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/shared/services/admin.service';
import { Customer } from 'src/app/shared/models/customer';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-update-customer',
    templateUrl: './update-customer.component.html',
    styleUrls: ['./update-customer.component.css']
})
export class UpdateCustomerComponent implements OnInit {

    customerId: number;
    customerToUpdate: Customer;
    wasChange = false;

    constructor(private adminService: AdminService, private httpClient: HttpClient) {

        //in order to prevent empty choice.
        this.customerId = this.adminService.allCustomers[0].id;

    }

    ngOnInit() {
        this.adminService.getAllCustomers(); //filling in for the select options.
        this.wasChange = false;

    }

    fillCustomerDetails() {
        this.httpClient.get<Customer>(`${this.adminService.baseUrl}customer/${this.customerId}`, {withCredentials: true})
            .subscribe(res => {
                this.customerToUpdate = res;
                this.wasChange = false; //disabling the submit button (unless changes are made)
            }, err => alert(err.error.messages));
    }

    submitChanges() {
        this.adminService.updateCustomer(this.customerToUpdate);
        this.wasChange = false; //disabling the submit button for next time (until new changes are made)
    }

    //disabling submit button until user actually enters/deletes something in form.
    enableSubmit() {
        this.wasChange = true;
    }

}
