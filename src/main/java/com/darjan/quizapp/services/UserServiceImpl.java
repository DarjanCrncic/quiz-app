package com.darjan.quizapp.services;

import java.util.List;

import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import com.darjan.quizapp.models.Provider;
import com.darjan.quizapp.models.User;
import com.darjan.quizapp.models.dtos.FacebookFriendsDTO;
import com.darjan.quizapp.models.dtos.FacebookFriendsDTO.FacebookUser;
import com.darjan.quizapp.models.dtos.PlayerAvgScore;
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
	public Long processOAuthPostLogin(String email, String username, String fullName, OAuth2AccessToken token) {
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
			System.out.println("updating existing user");
			
			FacebookUser user = facebookApi.getUserFacebookData(existUser.getUsername(), token.getTokenValue(), "name,id,picture");
			existUser.setFullName(user.getName());
			existUser.setImageUrl(user.getPicture().getData().getUrl());
			existUser.setUsername(user.getId());
			userRepository.save(existUser);
		}
		return existUser.getId();
	}

	@Override
	public FacebookFriendsDTO getFriendsGeneralData(CustomOAuth2User user) {
		FacebookFriendsDTO friendsDTO = facebookApi.getUserFriendsFacebookData(user.getName() + "/friends", user.getToken(), "name,id,picture");
		for (FacebookUser friend : friendsDTO.getData()) {
			PlayerAvgScore scoreData = quizRepository.getAverageScore(friend.getId());
			friend.setAverageScore(scoreData.getResult());
			friend.setDbId(scoreData.getUserId());
		}
		return friendsDTO;
	}
}
