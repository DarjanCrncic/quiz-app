package com.darjan.quizapp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info("using custom authentication provider...");
		CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(authentication.getName());

		if (userDetails == null || !userDetails.getUser().getUsername().equalsIgnoreCase(authentication.getName())) {
			throw new BadCredentialsException("User details with username: " + authentication.getName() + " not found.");
		}

		return new CustomAuthenticationToken(userDetails);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(CustomAuthenticationToken.class);
	}

}
