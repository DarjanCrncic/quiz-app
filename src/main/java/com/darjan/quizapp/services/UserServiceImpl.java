package com.darjan.quizapp.services;

import org.springframework.stereotype.Service;

import com.darjan.quizapp.models.User;
import com.darjan.quizapp.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
	
	UserRepository userRepository;

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
}
