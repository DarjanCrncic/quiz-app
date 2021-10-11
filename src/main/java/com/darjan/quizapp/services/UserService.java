package com.darjan.quizapp.services;

import java.util.List;

import com.darjan.quizapp.models.User;
import com.darjan.quizapp.models.dtos.FacebookFriendsDTO;
import com.darjan.quizapp.security.CustomUserDetails;

public interface UserService {

	User findById(Long id);

	List<User> findAll();

	FacebookFriendsDTO getFriendsGeneralData(CustomUserDetails user);

	Long processOAuthPostLogin(String username, String token);

}
