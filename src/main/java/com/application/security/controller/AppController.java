package com.application.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {


	@GetMapping("/login")
	public String login() {
		return "This is Login Page.....";
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "Thank You Login to proceed....";
	}
	

}
