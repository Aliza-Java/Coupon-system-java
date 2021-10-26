package jbcourse.couponSystemPhase3.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jbcourse.couponSystemPhase3.authentication.IAuthenticationFacade;
import jbcourse.couponSystemPhase3.entities.Company;
import jbcourse.couponSystemPhase3.entities.Customer;
import jbcourse.couponSystemPhase3.exceptions.IncompatibleInputException;
import jbcourse.couponSystemPhase3.exceptions.ObjectNotFoundException;
import jbcourse.couponSystemPhase3.services.AdminService;

@CrossOrigin(origins = "${cors.origin}")
@RestController
@RequestMapping("sec/admin")
public class AdminWebService {

	@Autowired
	AdminService adminService;
	
	@Autowired
	IAuthenticationFacade authenticationFacade;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
	public ResponseEntity handle() {
	    return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(path = "companies")
	public List<Company> findAllCompanies() {
		return adminService.getAllCompanies();
	}

	@PostMapping(path = "newcompany")
	public Company createCompany(@RequestBody Company company) throws IncompatibleInputException {
		return adminService.createCompany(company);
	}

	@RequestMapping(path = "company/{id}")
	public Company getCompany(@PathVariable long id) throws ObjectNotFoundException {
		return adminService.getCompanyById(id);
	}

	@PutMapping(path = "updatecompany/{id}")
	public void updateCompany(@PathVariable long id, @RequestBody Company company)
			throws IncompatibleInputException, ObjectNotFoundException {
		adminService.updateCompany(company, id);
	}

	@DeleteMapping(path = "removecompany/{id}")
	public boolean deleteCompany(@PathVariable long id) throws ObjectNotFoundException {
		adminService.removeCompany(id);
		return true;
	}

	@RequestMapping(path = "customers")
	public List<Customer> findAllCustomers() {
		return adminService.getAllCustomers();

	}

	@PostMapping(path = "newcustomer")
	public Customer createCustomer(@RequestBody Customer customer) throws IncompatibleInputException {
		return adminService.createCustomer(customer);
	}

	@RequestMapping(path = "customer/{id}")
	public Customer getCustomer(@PathVariable long id) throws ObjectNotFoundException {
		return adminService.getCustomerById(id);
	}

	@PutMapping(path = "updatecustomer/{id}")
	public void updateCustomer(@PathVariable long id, @RequestBody Customer customer)
			throws ObjectNotFoundException, IncompatibleInputException {
		customer.setId(id);
		adminService.updateCustomer(customer);
	}

	@DeleteMapping(path = "removecustomer/{id}")
	public void deleteCustomer(@PathVariable long id) throws ObjectNotFoundException {
		adminService.removeCustomer(id);
	}

	public void logout() {
		authenticationFacade.getAuthentication().setAuthenticated(false);
	}

}
