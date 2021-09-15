package com.darjan.quizapp.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darjan.quizapp.models.User;
import com.darjan.quizapp.security.CustomOAuth2User;
import com.darjan.quizapp.services.UserService;
import com.darjan.quizapp.utils.FacebookApi;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private FacebookApi facebookApi;
	
	@GetMapping("/users") 
	public List<User> getAllUsers(Principal principal) {
		return userService.findAll();
	}
	
	@GetMapping("/users/authenticated")
	public Principal getPrincipal(Principal principal) {
		return principal;
	}
	
	@GetMapping("/users/token")
	public String getPrincipalTokenTest(@AuthenticationPrincipal CustomOAuth2User user) {
		return facebookApi.getUserFacebookData(user.getName(), user.getToken(), "friends");
	}
	
	@GetMapping("/users/friends")
	public String getPrincipalFriends(@AuthenticationPrincipal CustomOAuth2User user) {
		return facebookApi.getUserFacebookData(user.getName() + "/friends", user.getToken(), "name,id,picture");
	}
}
