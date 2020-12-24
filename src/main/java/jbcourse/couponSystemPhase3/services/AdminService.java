package jbcourse.couponSystemPhase3.services;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.userdetails.UserDetails;

import jbcourse.couponSystemPhase3.entities.Company;
import jbcourse.couponSystemPhase3.entities.Customer;
import jbcourse.couponSystemPhase3.exceptions.IncompatibleInputException;
import jbcourse.couponSystemPhase3.exceptions.ObjectNotFoundException;

public interface AdminService {

	public boolean login(@NotNull @NotBlank String username, @NotNull @NotBlank String password);

	public Company createCompany(@Valid Company company) throws IncompatibleInputException;

	public void updateCompany(@NotNull(message = "Updated company cannot be null. ") @Valid Company company, long id)
			throws IncompatibleInputException, ObjectNotFoundException;

	public boolean removeCompany(long id) throws ObjectNotFoundException;

	public Company getCompanyById(long id) throws ObjectNotFoundException;

	public List<Company> getAllCompanies();

	public Customer createCustomer(@Valid Customer customer) throws IncompatibleInputException;

	public void updateCustomer(@NotNull(message = "Updated customer cannot be null. ") @Valid Customer customer)
			throws ObjectNotFoundException, IncompatibleInputException;

	public boolean removeCustomer(long id) throws ObjectNotFoundException;

	public Customer getCustomerById(long id) throws ObjectNotFoundException;

	public List<Customer> getAllCustomers();

	public UserDetails getAdminUserDetails(String username);

}
