package com.darjan.quizapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.darjan.quizapp.models.Provider;
import com.darjan.quizapp.models.User;
import com.darjan.quizapp.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	UserRepository userRepository;

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void processOAuthPostLogin(String email, String username, String fullName) {
		User existUser = userRepository.getUserByEmail(email);

		if (existUser == null) {
			System.out.println("saving new user");
			
			User newUser = new User();
			newUser.setEmail(email);
			newUser.setUsername(username);
			newUser.setFullName(fullName);
			newUser.setProvider(Provider.FACEBOOK);
			newUser.setEnabled(true);

			userRepository.save(newUser);
		} else {
			System.out.println("user already exist");
		}
	}
}
