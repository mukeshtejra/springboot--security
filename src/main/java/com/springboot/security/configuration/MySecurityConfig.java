package com.springboot.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import com.springboot.security.service.CustomUserDetailsService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //We can use annotation based Role access see in controller @PreAuthorize("hasROle(ADMIN)") on any rest service method that can only access by ADMIN role
//comment antMatchers with role while using @EnableGlobalMethodSecurity annotation
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		/** 
		 without disabling it, if you hit any post or put request it would not allow even though you are admin user so we disable it while calling it by Known API client
		 csrf is a attack and it must be enable on unknown client, you can search this on google
		*/
				
		//.csrf().disable() 
		
		/*
		  we can configure to see csrf token on cookies section on post main and pass that token in post service headers
		 
		   X-XSRF-TOKEN - ed2ce659-4627-4300-8cb6-5cf3438618db(this you can get it from any GET service)
		*/
		.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) 
		.and()
		
		.authorizeRequests()  //Authorize request 
		
		//it will not check any authentication for below end points
		//.antMatchers("/home","/login","/register").permitAll()
		
		/*
		   in above antMatcher we use different end point to allow as we have all in one controller
		   so we can have common antmathers with match the url which start with public and allow it's all url
		 */
		 
		//.antMatchers("/public/**").permitAll()
		
		.antMatchers("/signin").permitAll()
		
		//We can use ROLE based authorization for url
		.antMatchers("/public/**").hasRole("NORMAL")//mukesh can access only public url 
		.antMatchers("/users/**").hasRole("ADMIN") //rakesh can access only users url
		
		
		.anyRequest() // all request
		.authenticated()  // should be authenticated
		.and()
		
		//use basic authentication so it will override spring boot form based authentication to basic authentication
		//.httpBasic(); 
		.formLogin()
		
		//we can create sign in page now it will go on /signin instead /login
		.loginPage("/signin")
		//login processing url 
		.loginProcessingUrl("/dologin")
		.defaultSuccessUrl("/users/");
		
		
		//we can use form based login and commenting httpBasic
		
	
	}

	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("mukesh").password("1234").roles("NORMAL");
		auth.inMemoryAuthentication().withUser("rakesh").password("12345").roles("ADMIN");

	}*/
	
	//if you run code it will give error for passwordEncoder so we have to tell passwordEncoder with plain text 
	/*@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}*/
	
	
	//Below using BcryptPasswordEncoder to encrypt password
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//we use custom user save from DB instead below hard coded
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
		
		
		//auth.inMemoryAuthentication().withUser("mukesh").password(this.passwordEncoder().encode("1234")).roles("NORMAL");
		//auth.inMemoryAuthentication().withUser("rakesh").password(this.passwordEncoder().encode("12345")).roles("ADMIN");

		//ROLE - High level overview NORMAL - READ
		//AUTHORITY - Permission - Read
		//Admin is Role and it is having Read, Write, Update Authority
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	
	
}
