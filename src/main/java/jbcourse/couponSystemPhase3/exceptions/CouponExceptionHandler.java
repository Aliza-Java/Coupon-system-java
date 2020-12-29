package jbcourse.couponSystemPhase3.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jbcourse.couponSystemPhase3.CORSFilter;

@ControllerAdvice
public class CouponExceptionHandler {

	/*
	 * Originally all methods ended with including a new headers variable - changing
	 * that to allow cors in. return new ResponseEntity<Object>(apiError, new
	 * HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	 */

	private final Logger log = LoggerFactory.getLogger(CORSFilter.class);


	// Catch-All
	//@ExceptionHandler(Throwable.class)
	public ResponseEntity<Object> handleThrowable(Throwable e) {
		log.info(e.getMessage());
		ApiError apiError = new ApiError("SERVER_ERROR",
				"We are sorry, but something wrong happened. Please contact the admin.");
		return new ResponseEntity<Object>(apiError,  HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CouponSystemException.class)
	public ResponseEntity<Object> handleCouponSystemException(CouponSystemException e) {
		ApiError apiError = new ApiError("COUPON_SYSTEM_ERROR", e.getMessage());
		return new ResponseEntity<Object>(apiError,  HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException e) {
		ApiError apiError = new ApiError("OBJECT_NOT_FOUND_ERROR", e.getMessage());
		return new ResponseEntity<Object>(apiError,  HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ConversionFailedException.class)
	public ResponseEntity<Object> handleUnrecognizedLoginType(ConversionFailedException e) {
		// this handler was designed specifically for unrecognized login types, but it
		// will work for other instances when the input does not match and there is a
		// conversion failed error.
		ApiError apiError = new ApiError("BAD_REQUEST", e.getValue() + " is not an acceptable value in this case.");
		return new ResponseEntity<Object>(apiError,  HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {

		List<String> validationErrorMessages = new ArrayList<>();

		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		for (ConstraintViolation<?> violation : violations) {
			String message = "Invalid value " + violation.getInvalidValue() + " for " + violation.getPropertyPath()
					+ " : " +

					violation.getMessage();
			validationErrorMessages.add(message);
		}

		return new ResponseEntity<Object>(new WebApiError("Input is invalid", validationErrorMessages), 
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LoginException.class)
	public ResponseEntity<Object> handleLoginException(LoginException e) {
		ApiError apiError = new ApiError("FORBIDDEN_ERROR", e.getMessage());
		return new ResponseEntity<Object>(apiError,  HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(PermissionException.class)
	public ResponseEntity<Object> handlePermissionException(PermissionException e) {
		ApiError apiError = new ApiError("UNAUTHORIZED_ERROR", e.getMessage());
		return new ResponseEntity<Object>(apiError,  HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(NoCouponsLeftException.class)
	public ResponseEntity<Object> handleNoCouponsLeftException(NoCouponsLeftException e) {
		ApiError apiError = new ApiError("CONFLICT_ERROR", e.getMessage());
		return new ResponseEntity<Object>(apiError,  HttpStatus.CONFLICT);
	}

	@ExceptionHandler(CouponAlreadyPurchasedException.class)
	public ResponseEntity<Object> handleCouponAlreadyPurchased(CouponAlreadyPurchasedException e) {
		ApiError apiError = new ApiError("CONFLICT_ERROR", e.getMessage());
		return new ResponseEntity<Object>(apiError,HttpStatus.CONFLICT);
	}

	@ExceptionHandler(CouponDateException.class)
	public ResponseEntity<Object> handleCouponDateException(CouponDateException e) {
		ApiError apiError = new ApiError("BAD_REQUEST", e.getMessage());
		return new ResponseEntity<Object>(apiError,  HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	// this seems to be only for the postman data, as these things were not checked
	// by the spring validations.
	public ResponseEntity<Object> handleBadDate(HttpMessageNotReadableException e) {
		// If the error contains a complaint about the LocalDate type, the input is
		// directly related to the date.
		// otherwise, it could be any other input that doesn't match the expected type.
		ApiError apiError = e.getMessage().contains("Cannot deserialize value of type `java.time.LocalDate`")
				? new ApiError("BAD_REQUEST", "The dates you entered are not formatted correctly... try yyyy-mm-dd.")
				: new ApiError("BAD_REQUEST", "The input caused an error.");
		return new ResponseEntity<Object>(apiError,  HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IncompatibleInputException.class)
	public ResponseEntity<Object> handleDuplicateNames(IncompatibleInputException e) {
		ApiError apiError = new ApiError("CONFLICT", e.getMessage());
		return new ResponseEntity<Object>(apiError,  HttpStatus.CONFLICT);
	}

}
