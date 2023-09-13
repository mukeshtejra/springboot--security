package com.springboot.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class HomeController {

	@GetMapping("/home")
	public String getHomePage() {
		return "this is home page";
	}
	
	@GetMapping("/login")
	public String getLoginPage() {
		return "this is login page";
	}
	
	@GetMapping("/register")
	public String getRegisterPage() {
		return "this is registeration page";
	}
}
