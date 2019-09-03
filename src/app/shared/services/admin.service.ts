import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Customer } from '../models/customer';
import { Company } from '../models/company';


@Injectable({
    providedIn: 'root'
})
export class AdminService {

    baseUrl: string = "http://localhost:8080/CouponSystem/sec/admin/";

    public allCompanies: Company[];
    public allCustomers: Customer[];
    public companyToGet: Company;
    public customerToGet: Customer;
    public errorMessage: string;
    public resultMessage: string;
    public emptyList: boolean;
    public headers:HttpHeaders;


    public constructor(private httpClient: HttpClient) {
        this.errorMessage = "";
        this.resultMessage = "";
        this.emptyList = false;
            
    }

    resetMessages() {
        this.errorMessage = "";
        this.resultMessage = "";
        this.companyToGet = null;
        this.customerToGet = null;
        this.emptyList = false;
    }

    getAllCompanies() {

           this.httpClient.get<Company[]>(`${this.baseUrl}companies`,  {withCredentials:true })
            .subscribe(res => {
                this.allCompanies = res;
            },
                err => this.errorMessage = err.error.messages);
    }

    public createNewCompany(name: string, password: string, email: string): void {

        var addedCompany: Company = {
            "name": name,
            "password": password,
            "email": email
        };

        this.httpClient.post<Company>(`${this.baseUrl}newcompany`, addedCompany, { withCredentials: true })
            .subscribe(
                (res) => this.resultMessage = `Success! Company ${res.name} was created with id ${res.id}.`,
                (err) => { alert(err.error.messages) }
            );
    }

    public findCompanyNow(id: number) {
        this.httpClient.get<Company>(`${this.baseUrl}company/${id}`, { withCredentials: true })
            .subscribe(res => this.companyToGet = res, err => alert(err.error.messages));
    }

    public updateCompany(companyToUpdate: Company) {
        this.httpClient.put<Company>(`${this.baseUrl}updatecompany/${companyToUpdate.id}`, companyToUpdate, { withCredentials: true })
            .subscribe(() => alert(`Company ${companyToUpdate.id} has been successfully updated.`), err => alert("We could not update this company.  " + err.error.messages));
    }

    deleteCompany(id: number) {
        if (confirm('Are you sure you want to delete company of id ' + id + '?')) { //user must confirm his intention to delete
            this.httpClient.delete(`${this.baseUrl}removecompany/${id}`, { withCredentials: true })
                .subscribe(res => {
                    alert(`Company ${id} deleted successfully.`);
                    this.getAllCompanies();//update table
                }, err => {
                    alert("Unable to delete. " + err.error.messages);
                    this.getAllCompanies(); //Update dropdown with existing companies.
                }
                );
        } else {
            // Do nothing.  Giving user a chance to regret it.
        }
    }


    getAllCustomers() {
        this.httpClient.get<Customer[]>(`${this.baseUrl}customers`, { withCredentials: true })
            .subscribe(res => { this.allCustomers = res }, err => this.errorMessage = err);
    }

    public createNewCustomer(name: string, password: string) {

        var addedCustomer: Customer = {
            "name": name,
            "password": password,
        };

        this.httpClient.post<Customer>(`${this.baseUrl}newcustomer`, addedCustomer, { withCredentials: true }).subscribe(
            (res) => { this.resultMessage = `Success! Customer ${res.name} was created with id ${res.id}.`; },
            (err) => {
                alert(err.error.messages);
            });
    }

    public findCustomerNow(id: number) {
        this.httpClient.get<Customer>(`${this.baseUrl}customer/${id}`, { withCredentials: true })
            .subscribe(res => this.customerToGet = res, err => alert(err.error.messages));
    }

    public updateCustomer(customerToUpdate: Customer) {
        this.httpClient.put<Customer>(`${this.baseUrl}updatecustomer/` + customerToUpdate.id, customerToUpdate, { withCredentials: true })
            .subscribe(res => alert(`Customer ${customerToUpdate.id} has been successfully updated.`),
                err => alert("We could not update this customer. " + err.error.messages));
    }

    deleteCustomer(id: number) {
        if (confirm('Are you sure you want to delete customer of id ' + id + '?')) { //user must confirm his intention to delete
            this.httpClient.delete(`${this.baseUrl}removecustomer/${id}`, { withCredentials: true })
                .subscribe(res => {
                    alert(`Customer ${id} deleted successfully.`);
                    this.getAllCustomers(); //update table
                },
                    err => {
                        alert("Unable to delete. " + err.error.messages);
                        this.getAllCustomers(); //update dropdown with existing customers

                    });
        } else {
            // Do nothing.  Giving user a chance to regret it.
        }
    }
}