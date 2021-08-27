package com.darjan.quizapp.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.darjan.quizapp.models.dtos.ApiResponseObject;

@FeignClient(value = "data", url = "https://opentdb.com/api.php")
public interface QuestionsApi {
	
	@GetMapping()
	public ApiResponseObject randomGet(@RequestParam int amount, @RequestParam int category, @RequestParam String difficulty, @RequestParam String encode);
}
