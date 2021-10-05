package com.darjan.quizapp.controllers;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darjan.quizapp.models.User;
import com.darjan.quizapp.models.dtos.FacebookFriendsDTO;
import com.darjan.quizapp.security.CustomOAuth2User;
import com.darjan.quizapp.security.WebSecurityConfig;
import com.darjan.quizapp.services.UserService;

@RestController
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired
	private UserService userService;
	
	@GetMapping("/users") 
	public List<User> getAllUsers(Principal principal) {
		return userService.findAll();
	}
	
	@GetMapping("/users/authenticated")
	public Principal getPrincipal(Principal principal) {
		logger.info("trying authenticated...");
		return principal;
	}
	
	@GetMapping("/users/friends")
	public FacebookFriendsDTO getPrincipalFriends(@AuthenticationPrincipal CustomOAuth2User user) {
		return userService.getFriendsGeneralData(user);
	}
	
}
