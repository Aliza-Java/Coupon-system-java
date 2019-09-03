package jbcourse.couponSystemPhase3.exceptions;

@SuppressWarnings("serial")
public class CustomerNameAlreadyExistsException extends CouponSystemException {
	public CustomerNameAlreadyExistsException(String msg) {
		super(msg);
	}
}
