package com.darjan.quizapp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	@Autowired
	UserService userService;
 
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    	
        OAuth2User user =  super.loadUser(userRequest);
        logger.info("customOAuth2UserService: " + user.toString());
        
        OAuth2AccessToken token = userRequest.getAccessToken();
        //System.out.println(token.getTokenValue());
        
        String email = user.getAttribute("email");
        String fullName = user.getAttribute("name");
        String username = user.getAttribute("id");

        Long userId = userService.processOAuthPostLogin(email, username, fullName, token);
        
        return new CustomOAuth2User(user, token.getTokenValue(), userId);
    }
 
}