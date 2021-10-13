package com.darjan.quizapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.darjan.quizapp.models.dtos.AuthRequest;
import com.darjan.quizapp.models.dtos.FacebookDebugTokenDTO;
import com.darjan.quizapp.security.CustomAuthenticationToken;
import com.darjan.quizapp.security.JWTUtils;
import com.darjan.quizapp.services.UserService;
import com.darjan.quizapp.utils.FacebookApi;

@RestController
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	FacebookApi facebookApi;
	
	@Autowired
	JWTUtils jwtUtils;
	
	@Value("${spring.security.oauth2.client.registration.facebook.client-id}")
	String appId;
	
	@Value("${spring.security.oauth2.client.registration.facebook.client-secret}")
	String appSecret;
	
	@Autowired
	UserService userService;

	@PostMapping("/login")
	public String checkFacebookLogin(@RequestBody AuthRequest authRequest) {
		FacebookDebugTokenDTO response = facebookApi.checkTokenValidity(authRequest.getAccessToken(), appId + "|" + appSecret);
		logger.info("facebook debug_token response: " + response.toString());
		Authentication authentication = null;
		
		if (response.getData().isValid()) {
			// save new user to db or update existing one...
			userService.processOAuthPostLogin(authRequest.getUserID(), authRequest.getAccessToken());
			
			CustomAuthenticationToken authToken = new CustomAuthenticationToken(authRequest.getUserID());
			authentication = authenticationManager.authenticate(authToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			throw new BadCredentialsException("facebook token invalid...");
		}
		
		return 	jwtUtils.generateJwtToken(authentication);
	}
}
