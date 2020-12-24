package jbcourse.couponSystemPhase3.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jbcourse.couponSystemPhase3.entities.Company;
import jbcourse.couponSystemPhase3.entities.Customer;
import jbcourse.couponSystemPhase3.services.CompanyService;
import jbcourse.couponSystemPhase3.services.CustomerService;
import jbcourse.couponSystemPhase3.util_classes.LoginType;
import jbcourse.couponSystemPhase3.util_classes.User;
import jbcourse.couponSystemPhase3.util_classes.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	CompanyService companyService;

	@Autowired
	CustomerService customerService;

	@Value("${admin.username}")
	private String adminUsername;

	@Value("${admin.password}")
	private String adminPassword;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = null;

		// checking if username exists in any of the databases - admin, company or
		// customer
		if (adminUsername.equalsIgnoreCase(username)) {// it's admin
			user = new User(1, LoginType.ADMIN, adminUsername, passwordEncoder.encode(adminPassword));
		} else { // it's not admin
			Company company = companyService.getCompanyByName(username);
			if (company != null) { // it's company
				user = new User(company.getId(), LoginType.COMPANY, company.getName(),
						passwordEncoder.encode(company.getPassword()));
			} else { // it's not company - try one more last thing (customer)
				Customer customer = customerService.getCustomerByName(username);
				if (customer != null)// it's customer
					user = new User(customer.getId(), LoginType.CUSTOMER, customer.getName(),
							passwordEncoder.encode(customer.getPassword()));
			}
		}
		if (user == null) {//nothing worked
			throw new UsernameNotFoundException("No username '" + username + "' found.");
		}
		return UserDetailsImpl.build(user);

	}
}