package jbcourse.couponSystemPhase3.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jbcourse.couponSystemPhase3.entities.Coupon;
import jbcourse.couponSystemPhase3.repositories.CouponRepository;

@Service
public class UtilService {
	
	@Autowired
	CouponRepository couponRepository;

	//Regularly remove expired coupons.
	@Scheduled(fixedRateString = "${coupon.deletion.rate}")
	public void removeExpiredCoupons() {
		List<Coupon> expiredCoupons = couponRepository.findByEndDateBeforeOrderByIdAsc(LocalDate.now());
		for(Coupon c:expiredCoupons) {
			couponRepository.delete(c);
		}
	}
	
}
