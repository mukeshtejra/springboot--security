package com.springboot.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.security.model.User;

@Service
public class UserService {
	
	List<User> list = new ArrayList<>();
	
	UserService(){
		list.add(new User("abc", "abc", "test@gmail.com",null));
		list.add(new User("xyzabc", "abc", "xyz@gmail.com", null));
	}
	
	
	public List<User> getUsers(){
		
		return list;
		
	}
	
	public User getSingleUser(String username) {
		return this.list.stream().filter(obj -> username.equals(obj.getName())).findAny().orElse(null);
	}
	
	public void add(User user) {
		list.add(user);
		
	}

}
