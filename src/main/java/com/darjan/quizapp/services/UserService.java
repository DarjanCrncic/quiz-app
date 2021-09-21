package com.darjan.quizapp.services;

import java.util.List;

import com.darjan.quizapp.models.User;
import com.darjan.quizapp.models.dtos.FacebookFriendsDTO;
import com.darjan.quizapp.security.CustomOAuth2User;

public interface UserService {

	User findById(Long id);

	List<User> findAll();

	Long processOAuthPostLogin(String email, String username, String fullName);

	FacebookFriendsDTO getFriendsGeneralData(CustomOAuth2User user);

}
