package jbcourse.couponSystemPhase3.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jbcourse.couponSystemPhase3.authentication.IAuthenticationFacade;
import jbcourse.couponSystemPhase3.entities.Coupon;
import jbcourse.couponSystemPhase3.entities.Customer;
import jbcourse.couponSystemPhase3.exceptions.CouponAlreadyPurchasedException;
import jbcourse.couponSystemPhase3.exceptions.NoCouponsLeftException;
import jbcourse.couponSystemPhase3.exceptions.ObjectNotFoundException;
import jbcourse.couponSystemPhase3.services.CustomerService;
import jbcourse.couponSystemPhase3.util_classes.CouponCategory;

@CrossOrigin(origins = "${cors.origin}")
@RestController
@RequestMapping("sec/customer")
public class CustomerWebService {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	IAuthenticationFacade authenticationFacade;

//	@Autowired
//	HttpSession session;


	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
	public ResponseEntity handle() {
	    return new ResponseEntity(HttpStatus.OK);
	}
	
	
	//This method will be used throughout the controller to retrieve customer id and use it in the calls to service.
	private long currentCustomerId() {
		Authentication authentication = authenticationFacade.getAuthentication();
		return customerService.getCustomerByName(authentication.getName()).getId();

	}
	
	
	@RequestMapping(path = "details")
	public Customer getCustomerDetails() throws ObjectNotFoundException {
		return customerService.getCustomerById(currentCustomerId());
	}

	@PostMapping(path = "purchase/{couponId}")
	public ResponseEntity<String> purchaseCoupon(@PathVariable long couponId)
			throws ObjectNotFoundException, NoCouponsLeftException, CouponAlreadyPurchasedException {
		return customerService.purchaseCoupon(couponId, currentCustomerId());
	}

	@RequestMapping
	public List<Coupon> getCustomerCoupons() throws ObjectNotFoundException {
		return customerService.getCustomerCoupons(currentCustomerId());
	}

	@RequestMapping(path = "availablecoupons")
	public List<Coupon> getAvailableCoupons() throws ObjectNotFoundException {
		return customerService.getAvailableCoupons(currentCustomerId());
	}

	@RequestMapping(path = "bycategory/{category}")
	public List<Coupon> getCouponsByCategory(@PathVariable CouponCategory category) throws ObjectNotFoundException {
		return customerService.getAllCouponsByCategory(currentCustomerId(), category);
	}

	@RequestMapping(path = "byprice/{price}")
	public List<Coupon> getCouponsLessThanPrice(@PathVariable double price) throws ObjectNotFoundException {
		return customerService.getAllCouponsLessThanPrice(currentCustomerId(), price);
	}

	public void logout() {
//		authenticationFacade.getAuthentication().setAuthenticated(false);
		//session.invalidate();
	}

}
