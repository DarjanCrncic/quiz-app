package com.darjan.quizapp.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darjan.quizapp.models.User;
import com.darjan.quizapp.models.dtos.FacebookFriendsDTO;
import com.darjan.quizapp.security.CustomUserDetails;
import com.darjan.quizapp.services.UserService;

@RestController
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@GetMapping("/users") 
	public List<User> getAllUsers(@AuthenticationPrincipal CustomUserDetails user) {
		return userService.findAll();
	}
	
	@GetMapping("/users/authenticated")
	public CustomUserDetails getPrincipal(@AuthenticationPrincipal CustomUserDetails user) {
		logger.info("trying authenticated...");
		return user;
	}
	
	@GetMapping("/users/friends")
	public FacebookFriendsDTO getPrincipalFriends(@AuthenticationPrincipal CustomUserDetails user) {
		return userService.getFriendsGeneralData(user);
	}
	
}
