package com.springboot.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.security.model.User;

public interface UserRepository extends JpaRepository<User, String> {

	public User findByName(String username);

}
