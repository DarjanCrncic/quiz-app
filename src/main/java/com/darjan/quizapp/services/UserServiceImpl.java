package com.darjan.quizapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.darjan.quizapp.models.Provider;
import com.darjan.quizapp.models.User;
import com.darjan.quizapp.models.dtos.FacebookFriendsDTO;
import com.darjan.quizapp.models.dtos.FacebookFriendsDTO.FacebookUser;
import com.darjan.quizapp.repositories.QuizRepository;
import com.darjan.quizapp.repositories.UserRepository;
import com.darjan.quizapp.security.CustomOAuth2User;
import com.darjan.quizapp.utils.FacebookApi;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	UserRepository userRepository;
	QuizRepository quizRepository;
	FacebookApi facebookApi;
	
	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Long processOAuthPostLogin(String email, String username, String fullName) {
		User existUser = userRepository.getUserByEmail(email);
		
		if (existUser == null) {
			System.out.println("saving new user");
			
			User newUser = new User();
			newUser.setEmail(email);
			newUser.setUsername(username);
			newUser.setFullName(fullName);
			newUser.setProvider(Provider.FACEBOOK);
			newUser.setEnabled(true);

			existUser = userRepository.save(newUser);
		} else {
			System.out.println("user already exist");
		}
		return existUser.getId();
	}

	@Override
	public FacebookFriendsDTO getFriendsGeneralData(CustomOAuth2User user) {
		FacebookFriendsDTO friendsDTO = facebookApi.getUserFacebookData(user.getName() + "/friends", user.getToken(), "name,id,picture");
		for (FacebookUser friend : friendsDTO.getData()) {
			friend.setAverageScore(quizRepository.getAverageScore(friend.getId()));
		}
		return friendsDTO;
	}
}
