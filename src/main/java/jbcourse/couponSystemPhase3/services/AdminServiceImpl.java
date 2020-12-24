package jbcourse.couponSystemPhase3.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jbcourse.couponSystemPhase3.entities.Company;
import jbcourse.couponSystemPhase3.entities.Coupon;
import jbcourse.couponSystemPhase3.entities.Customer;
import jbcourse.couponSystemPhase3.exceptions.IncompatibleInputException;
import jbcourse.couponSystemPhase3.exceptions.ObjectNotFoundException;
import jbcourse.couponSystemPhase3.repositories.CompanyRepository;
import jbcourse.couponSystemPhase3.repositories.CustomerRepository;

@Validated
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Value("${spring.datasource.username}")
	private String adminUsername;

	@Value("${spring.datasource.password}")
	private String adminPassword;

	final static long NO_ID = -1; // Can be used when comparing with an id that definitely does not exist in the
									// database.

	@Override
	public boolean login(String username, String password) {

		return adminUsername.equals(username) && adminPassword.equals(password) ? true : false;

	}

	public UserDetails getAdminUserDetails(String username) throws UsernameNotFoundException {
		if (adminUsername.equals(username)) {
			//of course admin password is better encrypted.
			return new User(adminUsername, adminPassword,
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

	@Override
	public List<Company> getAllCompanies() {
		return companyRepository.findByOrderByIdAsc();
	}

	@Override
	public Company createCompany(Company company) throws IncompatibleInputException {
		if (isThisCompanyNameInUse(company.getName())) {
			throw new IncompatibleInputException("The chosen name is already in use.");
		}
		companyRepository.save(company);
		return company; // so that we may have the company's new id
	}

	@Override
	public Company getCompanyById(long id) throws ObjectNotFoundException {

		Optional<Company> optionalCompany = companyRepository.findById(id);
		if (!optionalCompany.isPresent()) {
			throw new ObjectNotFoundException("Company", id);
		}
		return optionalCompany.get();
	}

	@Override
	public void updateCompany(Company companyUpdatedInfo, long companyId)
			throws IncompatibleInputException, ObjectNotFoundException {
		// company cannot be null, as this was validated through spring validation in
		// the method signature.
		List<Coupon> saveTheCoupons; // Will be used to save the coupons as these are not updated
		try {
			Company existingCompany = getCompanyById(companyId);
			saveTheCoupons = existingCompany.getCoupons();

			// Checking if user is attempting to change company name
			if (!existingCompany.getName().equals(companyUpdatedInfo.getName())) {
				throw new IncompatibleInputException(" Company name cannot be changed.");
			}
		} catch (ObjectNotFoundException e) {
			throw new ObjectNotFoundException("Company", companyId);
		}
		companyUpdatedInfo.setCoupons(saveTheCoupons);
		companyRepository.save(companyUpdatedInfo);

	}

	@Override
	public boolean removeCompany(long id) throws ObjectNotFoundException {
		try {
			companyRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException("Company", id);
		}
		return true;

	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findByOrderByIdAsc();
	}

	@Override
	public Customer createCustomer(Customer customer) throws IncompatibleInputException {
		if (isThisCustomerNameInUse(customer.getName(), NO_ID)) {
			// in this case there is no need to check against this customers 'existing' id
			// (as done in updateCustomer) - this is a
			// new customer without an id.
			throw new IncompatibleInputException("The chosen name is already in use.");
		}
		customerRepository.save(customer);
		return customer; // so that we may have his new id.
	}

	@Override
	public Customer getCustomerById(long id) throws ObjectNotFoundException {

		Optional<Customer> optionalCustomer = customerRepository.findById(id);
		if (!optionalCustomer.isPresent()) {
			throw new ObjectNotFoundException("Customer", id);
		}
		return optionalCustomer.get();
	}

	@Override
	public void updateCustomer(Customer customer) throws ObjectNotFoundException, IncompatibleInputException {
		try {
			Customer existingCustomer = getCustomerById(customer.getId());

			// verifying user is not attempting to change customer name.
			if (!existingCustomer.getName().equals(customer.getName())) {
				throw new IncompatibleInputException("Customer name cannot be changed.");
			}
		} catch (ObjectNotFoundException e) {
			throw new ObjectNotFoundException("Customer", customer.getId());
		}

		customerRepository.save(customer);
	}

	@Override
	public boolean removeCustomer(long id) throws ObjectNotFoundException {
		try {
			customerRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException("Customer", id);
		}
		return true;
	}

	private boolean isThisCompanyNameInUse(String suggestedCompanyName) {
		List<Company> allCompanies = companyRepository.findAll();
		for (Company c : allCompanies) {
			if (c.getName().equals(suggestedCompanyName)) {
				return true;
			}
		}
		return false;
	}

	private boolean isThisCustomerNameInUse(String suggestedCustomerName, long customerId) {
		List<Customer> allCustomers = customerRepository.findAll();
		for (Customer c : allCustomers) {
			if (c.getName().equals(suggestedCustomerName) && c.getId() != customerId) {
				return true;
			}
		}
		return false;
	}

}
