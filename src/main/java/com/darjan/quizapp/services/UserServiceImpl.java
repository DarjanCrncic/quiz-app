package com.darjan.quizapp.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.darjan.quizapp.models.Provider;
import com.darjan.quizapp.models.User;
import com.darjan.quizapp.models.dtos.FacebookFriendsDTO;
import com.darjan.quizapp.models.dtos.FacebookFriendsDTO.FacebookUser;
import com.darjan.quizapp.models.dtos.PlayerAvgScore;
import com.darjan.quizapp.repositories.QuizRepository;
import com.darjan.quizapp.repositories.UserRepository;
import com.darjan.quizapp.security.CustomUserDetails;
import com.darjan.quizapp.security.WebSecurityConfig;
import com.darjan.quizapp.utils.FacebookApi;

@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepository;
	QuizRepository quizRepository;
	FacebookApi facebookApi;
	
	public UserServiceImpl(UserRepository userRepository, QuizRepository quizRepository, FacebookApi facebookApi) {
		super();
		this.userRepository = userRepository;
		this.quizRepository = quizRepository;
		this.facebookApi = facebookApi;
	}

	Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Long processOAuthPostLogin(String username, String token) {
		
		FacebookUser user = facebookApi.getUserFacebookData(username, token, "name,id,picture,email");
		User existUser = userRepository.getUserByEmail(user.getEmail());
		
		if (existUser == null) {
			logger.info("saving new user");
			
			User newUser = new User();
			newUser.setEmail(user.getEmail());
			newUser.setUsername(username);
			newUser.setFullName(user.getName());
			newUser.setProvider(Provider.FACEBOOK);
			newUser.setEnabled(true);
			newUser.setToken(token);
			existUser = userRepository.save(newUser);
		} else {
			logger.info("updating existing user");
			
			existUser.setToken(token);
			existUser.setFullName(user.getName());
			existUser.setImageUrl(user.getPicture().getData().getUrl());
			existUser.setUsername(user.getId());
			userRepository.save(existUser);
		}
		return existUser.getId();
	}

	@Override
	public FacebookFriendsDTO getFriendsGeneralData(CustomUserDetails userDetails) {
		FacebookFriendsDTO friendsDTO = facebookApi.getUserFriendsFacebookData(userDetails.getUsername() + "/friends", userDetails.getUser().getToken(), "name,id,picture");
		for (FacebookUser friend : friendsDTO.getData()) {
			PlayerAvgScore scoreData = quizRepository.getAverageScore(friend.getId());
			friend.setAverageScore(scoreData.getResult());
			friend.setDbId(scoreData.getUserId());
		}
		return friendsDTO;
	}
}
