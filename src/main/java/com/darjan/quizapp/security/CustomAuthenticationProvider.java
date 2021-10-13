package com.darjan.quizapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
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
