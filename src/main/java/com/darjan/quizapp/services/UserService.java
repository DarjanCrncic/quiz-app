package com.darjan.quizapp.services;

import java.util.List;

import com.darjan.quizapp.models.User;

public interface UserService {

	User findById(Long id);

	List<User> findAll();

	Long processOAuthPostLogin(String email, String username, String fullName);

}
