package jbcourse.couponSystemPhase3.exceptions;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/*
 * Errors will be sent in httpResponse with this model.  
 * */

@Getter
@Setter
public class ApiError {

	private String code;
	private List<String> messages; // there may be more than one message (such as in validations)

	public ApiError(String code, String... messages) {
		this.code = code;
		this.messages = Arrays.asList(messages);
	}

	@Override
	public String toString() {
		return "apiError [code=" + code + ", messages=" + messages + "]";
	}

}
