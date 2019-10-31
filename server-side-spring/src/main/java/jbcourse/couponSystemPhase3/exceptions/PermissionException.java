package jbcourse.couponSystemPhase3.exceptions;

/*This is thrown when PermissionAspect identifies a company/customer 
 * accessing a method not within his user type.*/

@SuppressWarnings("serial")
public class PermissionException extends Exception {
	public PermissionException(String msg) {
		super(msg);
	}
}
