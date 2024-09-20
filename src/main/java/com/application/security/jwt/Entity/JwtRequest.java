package com.application.security.jwt.Entity;


public class JwtRequest {

	private String userEmail;
	private String userPassword;
	
	private String role;
	
	public JwtRequest(String userEmail, String userPassword) {
		super();
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	@Override
	public String toString() {
		return "JwtRequest [userEmail=" + userEmail + ", userPassword=" + userPassword + "]";
	}

	
}
