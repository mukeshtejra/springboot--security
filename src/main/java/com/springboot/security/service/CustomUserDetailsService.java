package com.springboot.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.security.model.CustomUserDetails;
import com.springboot.security.model.User;
import com.springboot.security.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	
	@SuppressWarnings("deprecation")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByName(username);
		if(user ==null) {
			throw new UsernameNotFoundException("No User");
		}
		return new CustomUserDetails(user);
	}

}
