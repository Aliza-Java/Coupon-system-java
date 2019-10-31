package jbcourse.couponSystemPhase3.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jbcourse.couponSystemPhase3.entities.Company;
import jbcourse.couponSystemPhase3.entities.Coupon;
import jbcourse.couponSystemPhase3.exceptions.CouponDateException;
import jbcourse.couponSystemPhase3.exceptions.ObjectNotFoundException;
import jbcourse.couponSystemPhase3.exceptions.PermissionException;
import jbcourse.couponSystemPhase3.repositories.CompanyRepository;
import jbcourse.couponSystemPhase3.repositories.CouponRepository;
import jbcourse.couponSystemPhase3.util_classes.CouponCategory;

@Validated
@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	CouponRepository couponRepository;

	@Override
	public Company login(String username, String password) {
		Optional<Company> optionalCompany = companyRepository.getCompanyByNameAndPassword(username, password);
		if (!optionalCompany.isPresent()) {
			return null;
		}
		return optionalCompany.get();
	}

	public Company getCompanyById(long companyId) {
		Optional<Company> optionalCompany = companyRepository.findById(companyId);
		if (!optionalCompany.isPresent()) {
			return null;
		}
		return optionalCompany.get();

	}

	@Override
	public List<Coupon> getAllCoupons(long companyId) {
		return couponRepository.findAllByCompanyIdOrderByIdAsc(companyId);
	}

	@Override
	public Coupon createCoupon(Coupon coupon, long companyId) throws CouponDateException {
		Company company = companyRepository.getOne(companyId); // find company Id to put into coupon
		validateCouponDates(coupon.getStartDate(), coupon.getEndDate());
		coupon.setCompany(company);
		couponRepository.save(coupon);
		return coupon; // So that we can have the coupon's id
	}

	@Override
	public Coupon getCouponById(long couponId, long companyId) throws ObjectNotFoundException, PermissionException {

		Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);
		if (!optionalCoupon.isPresent()) {
			throw new ObjectNotFoundException("Coupon", couponId);
		} else {
			checkCompanyPermission(optionalCoupon.get().getCompany().getId(), companyId);
		}
		return optionalCoupon.get();
	}

	@Override
	public void updateCoupon(Coupon updatedCoupon, long companyId)
			throws PermissionException, ObjectNotFoundException, CouponDateException {
		
		/*
		 * 	Incoming coupon does not register company although it is sent from client. 
		 *  Here we retrieve the coupon from the database to check its company for permission reasons.
		 */
		
		Coupon retrievedCoupon = getCouponById(updatedCoupon.getId(), companyId);
		Company verifiedCompany = retrievedCoupon.getCompany();
		checkCompanyPermission(verifiedCompany.getId(), companyId);
		
		if (couponRepository.findById(updatedCoupon.getId()) == null) {
			throw new ObjectNotFoundException("Coupon", updatedCoupon.getId());
		}

		validateCouponDates(updatedCoupon.getStartDate(), updatedCoupon.getEndDate());
		updatedCoupon.setCompany(verifiedCompany);
		couponRepository.save(updatedCoupon);

	}

	@Override
	@Transactional
	public void removeCoupon(long couponId, long companyId) throws ObjectNotFoundException, PermissionException {
		try {
			checkCompanyPermission(couponRepository.getOne(couponId).getCompany().getId(), companyId);

			Coupon coupon = getCouponById(couponId, companyId);
			couponRepository.save(coupon);
			couponRepository.deleteById(couponId);
		} catch (EmptyResultDataAccessException | EntityNotFoundException e) {
			throw new ObjectNotFoundException("Coupon", couponId);
		}
	}

	@Override
	public List<Coupon> getCouponsLessThanPrice(double price, long companyId) {
		return couponRepository.findAllByCompanyIdAndPriceLessThanEqualOrderByIdAsc(companyId, price);
	}

	@Override
	public List<Coupon> getCouponsBeforeDate(LocalDate endDate, long companyId) {
		return couponRepository.findByCompanyIdAndEndDateLessThanEqualOrderByIdAsc(companyId, endDate);
	}

	@Override
	public List<Coupon> getCouponsByCategory(CouponCategory category, long companyId) {
		return couponRepository.findByCompanyIdAndCategoryOrderByIdAsc(companyId, category);
	}

	private void checkCompanyPermission(long companyIdOfCoupon, long loggedCompanyId) throws PermissionException {
		// Verifying that company actually owns this coupon and can touch only its
		// coupons
		if (companyIdOfCoupon != loggedCompanyId) {
			throw new PermissionException("This coupon does not belong to your company.");
		}
	}

	// A helper method for updating coupon and creating new one.
	private void validateCouponDates(LocalDate startDate, LocalDate endDate) throws CouponDateException {
		if (startDate.isAfter(endDate)) {
			throw new CouponDateException("The coupon's expiry date must not be before its start date.");
		}

	}

}
