package jbcourse.couponSystemPhase3.exceptions;

/*
 * A more general exception that can be specified to any entity that is not found - 
 * coupon, customer or company
 * */

@SuppressWarnings("serial")
public class ObjectNotFoundException extends CouponSystemException {

	public ObjectNotFoundException(String type, long id) {
		super(type + " of id " + id + " not found.");
	}

}
