package com.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springboot.security.model.User;
import com.springboot.security.repository.UserRepository;

@SpringBootApplication
public class SpringbootSecurityApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSecurityApplication.class, args);
	}

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		
		//we have to use ROLE and underscore with role name while saving to DB
		User user = new User("mukesh", bCryptPasswordEncoder.encode("1234"), "mukesh@gmail.com", "ROLE_ADMIN");
		repository.save(user);
		
		User user1 = new User("rakesh", bCryptPasswordEncoder.encode("12345"), "rakesh@gmail.com", "ROLE_NORMAL");
		repository.save(user1);
		
	}

}
