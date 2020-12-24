//package jbcourse.couponSystemPhase3.rest;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import jbcourse.couponSystemPhase3.authentication.Login;
//import jbcourse.couponSystemPhase3.entities.Company;
//import jbcourse.couponSystemPhase3.entities.Customer;
//import jbcourse.couponSystemPhase3.exceptions.CouponSystemException;
//import jbcourse.couponSystemPhase3.exceptions.LoginException;
//import jbcourse.couponSystemPhase3.services.AdminService;
//import jbcourse.couponSystemPhase3.services.CompanyService;
//import jbcourse.couponSystemPhase3.services.CustomerService;
//import jbcourse.couponSystemPhase3.util_classes.LoginType;
//import jbcourse.couponSystemPhase3.util_classes.User;
//
//@RestController
//@RequestMapping("log") // path will continue with 'in' or 'out', depending on user's choice
//public class LoginWebService {
//
//	@Autowired
//	AdminService adminService;
//
//	@Autowired
//	CompanyService companyService;
//
//	@Autowired
//	CustomerService customerService;
//
//	@Autowired
//	HttpServletRequest request;
//	
//	boolean success = false;
//	User user = null;
//
//	@PostMapping("in")
//	public User login(@RequestBody Login login) throws LoginException {
//		success = false;
//
//		switch (login.getType()) {
//			case ADMIN:
//				if (adminService.login(login.getUsername(), login.getPassword())) {
//					HttpSession session = request.getSession();
//					user = new User(0, LoginType.ADMIN);
//					session.setAttribute("user", user);
//
//
//					success = true;
//				}
//				break;
//			case COMPANY: {
//				Company company = new Company(); //just testing.
//				if (company != null) {
//					HttpSession session = request.getSession();
//					user = new User(company.getId(), LoginType.COMPANY);
//					session.setAttribute("user", user);
//
//					success = true;
//				}
//			}
//				break;
//			case CUSTOMER: {
//
//				Customer customer = customerService.login(login.getUsername(), login.getPassword());
//				if (customer != null) {
//					HttpSession session = request.getSession();
//					user = new User(customer.getId(), LoginType.CUSTOMER);
//					session.setAttribute("user", user);
//
//					success = true;
//				}
//			}
//				break;
//			default:
//				// If any other value will be entered, CouponExceptionHandler will throw an
//				// error stating that the value is not acceptable.
//
//		}
//
//		if (success != true) // login did not go through in switch statement
//		{
//			throw new LoginException("Unable to log in as " + login.getType().toString().toLowerCase()
//					+ " with the credentials you provided.");
//		}
//		return user;
//	}
//
//
//
//	@PostMapping("out")
//	public void logout() throws CouponSystemException {
//		try {
//			request.getSession(false).invalidate();
//		} catch (Throwable e) {
//			throw new CouponSystemException("Ok, you're logged out.  But it didn't go so smoothly for some reason. ");
//		}
//
//	}
//}
