package jbcourse.couponSystemPhase3.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jbcourse.couponSystemPhase3.entities.Coupon;
import jbcourse.couponSystemPhase3.entities.Customer;
import jbcourse.couponSystemPhase3.exceptions.CouponAlreadyPurchasedException;
import jbcourse.couponSystemPhase3.exceptions.NoCouponsLeftException;
import jbcourse.couponSystemPhase3.exceptions.ObjectNotFoundException;
import jbcourse.couponSystemPhase3.services.CustomerService;
import jbcourse.couponSystemPhase3.util_classes.CouponCategory;
import jbcourse.couponSystemPhase3.util_classes.User;

@RestController
@RequestMapping("sec/customer")
//overcoming CORS while allowing cookies
//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class CustomerWebService {

	@Autowired
	CustomerService customerService;

	@Autowired
	HttpSession session;

	@RequestMapping(path = "details")
	public Customer getCustomerDetails() throws ObjectNotFoundException {
		return customerService.getCustomerById(((User) session.getAttribute("user")).getId());
	}

	@PostMapping(path = "purchase/{couponId}")
	public ResponseEntity<String> purchaseCoupon(@PathVariable long couponId)
			throws ObjectNotFoundException, NoCouponsLeftException, CouponAlreadyPurchasedException {
		return customerService.purchaseCoupon(couponId, ((User) session.getAttribute("user")).getId());
	}

	@RequestMapping
	public List<Coupon> getCustomerCoupons() throws ObjectNotFoundException {
		return customerService.getCustomerCoupons(((User) session.getAttribute("user")).getId());
	}

	@RequestMapping(path = "availablecoupons")
	public List<Coupon> getAvailableCoupons() throws ObjectNotFoundException {
		return customerService.getAvailableCoupons(((User) session.getAttribute("user")).getId());
	}

	@RequestMapping(path = "bycategory/{category}")
	public List<Coupon> getCouponsByCategory(@PathVariable CouponCategory category) throws ObjectNotFoundException {
		return customerService.getAllCouponsByCategory(((User) session.getAttribute("user")).getId(), category);
	}

	@RequestMapping(path = "byprice/{price}")
	public List<Coupon> getCouponsLessThanPrice(@PathVariable double price) throws ObjectNotFoundException {
		return customerService.getAllCouponsLessThanPrice(((User) session.getAttribute("user")).getId(), price);
	}

	public void logout() {
		session.invalidate();
	}

}
