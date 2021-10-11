package com.darjan.quizapp.security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.darjan.quizapp.models.User;
import com.darjan.quizapp.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	@Autowired
	private UserRepository authUserRepository;
	
	@Override
	public CustomUserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User authUser = authUserRepository.findByUsername(userName).orElse(null);
		if (authUser == null) {
			throw new UsernameNotFoundException("User not found");
		}
		logger.info("inside custom user details, user found");
		return new CustomUserDetails(authUser);
	}
}