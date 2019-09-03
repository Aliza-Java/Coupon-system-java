package jbcourse.couponSystemPhase3.exceptions;

@SuppressWarnings("serial")
public class CouponAlreadyPurchasedException extends Exception {
	public CouponAlreadyPurchasedException(String msg) {
		super(msg);
	}
}
