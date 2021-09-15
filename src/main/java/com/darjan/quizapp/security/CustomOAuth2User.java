package com.darjan.quizapp.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User{

	private OAuth2User oAuth2User;
	private String token;
	private Long id;
	
	public CustomOAuth2User(OAuth2User oAuth2User, String token, Long id) {
        this.oAuth2User = oAuth2User;
        this.token = token;
        this.id = id;
    }
	
	@Override
	public Map<String, Object> getAttributes() {
		return oAuth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return oAuth2User.getAuthorities();
	}

	@Override
	public String getName() {
		return oAuth2User.getName();
	}
	
	public String getEmail() {
		return oAuth2User.getAttribute("email");
	}
		
	public String getToken() {
		return token;
	}
	
	public Long getId() {
		return id;
	}
}
