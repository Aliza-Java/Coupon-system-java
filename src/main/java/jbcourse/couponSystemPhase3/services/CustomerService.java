package jbcourse.couponSystemPhase3.services;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import jbcourse.couponSystemPhase3.entities.Coupon;
import jbcourse.couponSystemPhase3.entities.Customer;
import jbcourse.couponSystemPhase3.exceptions.CouponAlreadyPurchasedException;
import jbcourse.couponSystemPhase3.exceptions.LoginException;
import jbcourse.couponSystemPhase3.exceptions.NoCouponsLeftException;
import jbcourse.couponSystemPhase3.exceptions.ObjectNotFoundException;
import jbcourse.couponSystemPhase3.util_classes.CouponCategory;

public interface CustomerService {

	public Customer getCustomerById(long id) throws ObjectNotFoundException;
	
	public Customer getCustomerByName(String name);
	
	public ResponseEntity<String> purchaseCoupon(long couponId, long customerId) throws ObjectNotFoundException, NoCouponsLeftException, CouponAlreadyPurchasedException;
	
	public List<Coupon> getAvailableCoupons(long customerId) throws ObjectNotFoundException;
	
	public List<Coupon> getCustomerCoupons(long customerId) throws ObjectNotFoundException;
	
	public List<Coupon> getAllCouponsByCategory(long customerId, CouponCategory category) throws ObjectNotFoundException;
	
	public List<Coupon> getAllCouponsLessThanPrice(long customerId, double price) throws ObjectNotFoundException;

	Customer login(@NotNull @NotBlank String username, @NotNull @NotBlank String password) throws LoginException;

	//public UserDetails getCustomerUserDetails(long id) throws ObjectNotFoundException;
	
}
