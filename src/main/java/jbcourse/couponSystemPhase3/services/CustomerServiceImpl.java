package jbcourse.couponSystemPhase3.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jbcourse.couponSystemPhase3.entities.Coupon;
import jbcourse.couponSystemPhase3.entities.Customer;
import jbcourse.couponSystemPhase3.exceptions.CouponAlreadyPurchasedException;
import jbcourse.couponSystemPhase3.exceptions.NoCouponsLeftException;
import jbcourse.couponSystemPhase3.exceptions.ObjectNotFoundException;
import jbcourse.couponSystemPhase3.repositories.CouponRepository;
import jbcourse.couponSystemPhase3.repositories.CustomerRepository;
import jbcourse.couponSystemPhase3.util_classes.CouponCategory;

@Validated
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CouponRepository couponRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Customer login(String username, String password) {
		Optional<Customer> optionalCustomer = customerRepository.getCustomerByNameAndPassword(username, password);
		if (!optionalCustomer.isPresent()) {
			return null;
		}
		return optionalCustomer.get();
	}

	@Override
	public Customer getCustomerById(long id) throws ObjectNotFoundException {

		Optional<Customer> optionalCustomer = customerRepository.findById(id);
		if (!optionalCustomer.isPresent()) {
			throw new ObjectNotFoundException("Customer", id);
		} // if
		return optionalCustomer.get();
	}// method

//	public UserDetails getCustomerUserDetails(long customerId) throws ObjectNotFoundException {
//		Customer customer = getCustomerById(customerId);
//		if (customer != null) {
//			return new User(customer.getName(), customer.getPassword(), new ArrayList<>());
//		} else {
//			throw new ObjectNotFoundException("Customer", customerId);
//		}
//	}

	public Customer getCustomerByName(String name) {
		Optional<Customer> optionalCustomer = customerRepository.findByName(name);
		if (!optionalCustomer.isPresent()) {
			return null;
		}
		return optionalCustomer.get();
	}
	
	@Override
	@Transactional
	public ResponseEntity<String> purchaseCoupon(long couponId, long customerId)
			throws ObjectNotFoundException, NoCouponsLeftException, CouponAlreadyPurchasedException {
		Customer customer = getCustomerById(customerId);
		Coupon coupon = getCouponById(couponId);

		// checking if customer has already purchased this coupon:
		List<Coupon> customerCoupons = customer.getCoupons();
		if (checkIfCouponAlreadyPurchased(customerCoupons, couponId))
			throw new CouponAlreadyPurchasedException("You have already purchased coupon of id: " + couponId);

		if (coupon.getAmount() <= 0)
			throw new NoCouponsLeftException("There are no coupons left of this type.");

		// if both tests have passed - there are available coupons and customer has not
		// purchased this coupon, continue with purchase:
		customerCoupons.add(coupon);
		customer.setCoupons(customerCoupons);
		customerRepository.save(customer);
		couponRepository.decrementCouponAmount(couponId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	public List<Coupon> getCustomerCoupons(long customerId) throws ObjectNotFoundException {
		List<Coupon> customerCoupons = getCustomerById(customerId).getCoupons();
		customerCoupons.sort(Coupon.byId);
		return customerCoupons;

	}

	@Override
	public List<Coupon> getAvailableCoupons(long customerId) throws ObjectNotFoundException {
		List<Coupon> allCoupons = couponRepository.findAllByOrderByIdAsc();
		List<Coupon> customerCoupons = getCustomerCoupons(customerId);

		List<Coupon> availableCoupons = new ArrayList<>();
		for (Coupon c : allCoupons) {
			if (!checkIfCouponAlreadyPurchased(customerCoupons, c.getId())) {
				availableCoupons.add(c);
			}
		}

		return availableCoupons;
	}

	@Override
	public List<Coupon> getAllCouponsByCategory(long customerId, CouponCategory category)
			throws ObjectNotFoundException {
		Customer customer = getCustomerById(customerId);
		List<Coupon> coupons = customer.getCoupons();
		coupons.sort(Coupon.byId);
		List<Coupon> couponsByCategory = new ArrayList<>();

		for (Coupon c : coupons) {
			if (c.getCategory() == category) {
				couponsByCategory.add(c);
			} // if
		} // for
		return couponsByCategory;
	}

	@Override
	public List<Coupon> getAllCouponsLessThanPrice(long customerId, double price) throws ObjectNotFoundException {
		Customer customer = getCustomerById(customerId);
		List<Coupon> coupons = customer.getCoupons();
		coupons.sort(Coupon.byId);
		List<Coupon> couponsByPrice = new ArrayList<>();

		for (Coupon c : coupons) {
			if (c.getPrice() <= price) {
				couponsByPrice.add(c);
			} // if
		} // for
		return couponsByPrice;
	}

	// A helper method for purchaseCoupon.
	private Coupon getCouponById(long couponId) throws ObjectNotFoundException {
		Optional<Coupon> coupon = couponRepository.findById(couponId);
		if (!coupon.isPresent()) {
			throw new ObjectNotFoundException("Coupon", couponId);
		} else {
			return coupon.get();
		}
	}

	// A helper method to check if customer has already purchased this coupon.
	private boolean checkIfCouponAlreadyPurchased(List<Coupon> customerCoupons, long couponId) {
		for (Coupon c : customerCoupons) {
			if (c.getId() == couponId) {
				return true;
			}
		}
		return false;
	}

}
