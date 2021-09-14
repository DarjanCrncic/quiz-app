package com.darjan.quizapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.darjan.quizapp.services.UserService;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService  {
	
	@Autowired
	UserService userService;
 
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    	
        OAuth2User user =  super.loadUser(userRequest);
        System.out.println("customOAuth2UserService: " + user.toString());
        
        OAuth2AccessToken token = userRequest.getAccessToken();
        System.out.println(token.toString());
        
        String email = user.getAttribute("email");
        String fullName = user.getAttribute("name");
        String username = user.getAttribute("id");
        
        userService.processOAuthPostLogin(email, username, fullName);
        
        return new CustomOAuth2User(user);
    }
 
}