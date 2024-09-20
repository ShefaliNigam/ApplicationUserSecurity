package com.application.security.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	
	
	
	
	@ExceptionHandler(BadCredentialsException.class)
	public String handleWrongPassword( BadCredentialsException ex) {
		return "Wrong Password";
		
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public String handleWrongCredentials(UsernameNotFoundException ex) {
		return "Login Failed";
	}
	

	@ExceptionHandler(NoSuchElementException.class)
	public String NoDetails(NoSuchElementException ex) {
		return "Incorrect UserName and Password";
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public String userAlreadyExistsException(UserAlreadyExistsException ex) {
		return "User Already Exists";
	}
}
