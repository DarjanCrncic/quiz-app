package com.darjan.quizapp.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.darjan.quizapp.models.dtos.FacebookFriendsDTO;

@FeignClient(value = "facebook", url = "https://graph.facebook.com/v12.0")
public interface FacebookApi {

	@GetMapping("/{userId}")
	public FacebookFriendsDTO getUserFacebookData(@PathVariable String userId, @RequestParam String access_token, @RequestParam String fields);
	
}
