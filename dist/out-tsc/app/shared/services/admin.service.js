import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
let AdminService = class AdminService {
    constructor(httpClient) {
        this.httpClient = httpClient;
        this.baseUrl = "http://localhost:8080/CouponSystem/sec/admin/";
        this.errorMessage = "";
        this.resultMessage = "";
    }
    resetMessages() {
        this.errorMessage = "";
        this.resultMessage = "";
        this.companyToGet = null;
        this.customerToGet = null;
    }
    getAllCompanies() {
        this.httpClient.get(`${this.baseUrl}companies`)
            .subscribe(res => {
            this.allCompanies = res;
            //make this better/in all functions.
            if (res == []) {
                this.errorMessage == "Looks like there was nothing to retrieve.";
            }
        }, err => this.errorMessage = err.error.messages);
    }
    createNewCompany(name, password, email) {
        var addedCompany = {
            "name": name,
            "password": password,
            "email": email
        };
        this.httpClient.post(`${this.baseUrl}newcompany`, addedCompany).subscribe((res) => this.resultMessage = `Success! Company ${res.name} was created with id ${res.id}.`, (err) => { alert(err.error.messages); });
    }
    findCompanyNow(id) {
        this.httpClient.get(`${this.baseUrl}company/${id}`)
            .subscribe(res => this.companyToGet = res, err => alert(err.error.messages));
    }
    updateCompany(companyToUpdate) {
        this.httpClient.put(`${this.baseUrl}updatecompany/${companyToUpdate.id}`, companyToUpdate)
            .subscribe(() => alert(`Company ${companyToUpdate.id} has been successfully updated.`), err => alert("We could not update this company.  " + err.error.messages));
    }
    deleteCompany(id) {
        if (confirm('Are you sure you want to delete company of id ' + id + '?')) { //user must confirm his intention to delete
            this.httpClient.delete(`${this.baseUrl}removecompany/${id}`)
                .subscribe(res => {
                alert(`Company ${id} deleted successfully.`);
                this.getAllCompanies(); //update table
            }, err => {
                alert("Unable to delete. " + err.error.messages);
                this.getAllCompanies(); //fix dropdown with existing companies.
            });
        }
        else {
            // Do nothing.  Giving user a chance to regret it.
        }
    }
    getAllCustomers() {
        this.httpClient.get(`${this.baseUrl}customers`)
            .subscribe(res => { this.allCustomers = res; }, err => this.errorMessage = err);
    }
    createNewCustomer(name, password) {
        var addedCustomer = {
            "name": name,
            "password": password,
        };
        this.httpClient.post(`${this.baseUrl}newcustomer`, addedCustomer).subscribe((res) => { this.resultMessage = `Success! Customer ${res.name} was created with id ${res.id}.`; }, (err) => {
            alert(err.error.messages);
        });
    }
    findCustomerNow(id) {
        this.httpClient.get(`${this.baseUrl}customer/${id}`)
            .subscribe(res => this.customerToGet = res, err => alert(err.error.messages));
    }
    updateCustomer(customerToUpdate) {
        this.httpClient.put(`${this.baseUrl}updatecustomer/` + customerToUpdate.id, customerToUpdate)
            .subscribe(res => alert(`Customer ${customerToUpdate.id} has been successfully updated.`), err => alert("We could not update this customer. " + err.error.messages));
    }
    deleteCustomer(id) {
        if (confirm('Are you sure you want to delete customer of id ' + id + '?')) { //user must confirm his intention to delete
            this.httpClient.delete(`${this.baseUrl}removecustomer/${id}`)
                .subscribe(res => {
                alert(`Customer ${id} deleted successfully.`);
                this.getAllCustomers(); //update table
            }, err => {
                alert("Unable to delete. " + err.error.messages);
                this.getAllCustomers(); //fix dropdown with existing customers
            });
        }
        else {
            // Do nothing.  Giving user a chance to regret it.
        }
    }
};
AdminService = tslib_1.__decorate([
    Injectable({
        providedIn: 'root'
    })
], AdminService);
export { AdminService };
//# sourceMappingURL=admin.service.js.map