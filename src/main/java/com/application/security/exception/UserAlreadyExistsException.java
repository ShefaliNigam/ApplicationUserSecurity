package com.application.security.exception;

public class UserAlreadyExistsException extends RuntimeException{

	public UserAlreadyExistsException(String message) {
		super();
		// TODO Auto-generated constructor stub
		message = "User Already Exists";
	}

	
}
