package com.application.security.exception;

public class NoDetailsException extends RuntimeException{
 
	public NoDetailsException(String message) {
		super();
		message = "No Deatils IN DB";
	}
	
}
