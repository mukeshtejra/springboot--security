package com.springboot.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.security.model.User;
import com.springboot.security.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	
	
	
	@GetMapping("/")
	public List<User> getAllUsers(){
		return service.getUsers();
	
		
	}
	
	//@PreAuthorize("hasROle(ADMIN)")
	@GetMapping("/{username}")
	public User getSingleUser(@PathVariable String username) {
		return service.getSingleUser(username);
		
	}
	
	@PostMapping("/")
	public void addUser(@RequestBody User user) {
		service.add(user);
	}
	

}
