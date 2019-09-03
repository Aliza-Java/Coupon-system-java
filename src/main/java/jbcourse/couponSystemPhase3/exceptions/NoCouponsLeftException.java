package jbcourse.couponSystemPhase3.exceptions;

@SuppressWarnings("serial")
public class NoCouponsLeftException extends Exception {
	public NoCouponsLeftException(String msg) {
		super(msg);
	}

}
