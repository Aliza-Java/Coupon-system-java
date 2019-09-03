package jbcourse.couponSystemPhase3.exceptions;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class WebApiError {

	/*
	 * A class built for reporting validation problems.
	 * mainMessage is the general one that the input is invalid
	 * messages are the specific ones.
	 * 
	 * */
	
	String mainMessage;
	List<String> messages;

	public WebApiError(String mainMessage, List<String> messages) {

	
		this.mainMessage = mainMessage;
		this.messages = messages;
	}

	
	
	
}