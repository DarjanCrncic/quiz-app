package com.darjan.quizapp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darjan.quizapp.models.Question;
import com.darjan.quizapp.utils.QuestionsApi;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ApiController {

	private QuestionsApi questionsApi;
	
	@GetMapping("/test")
	public List<Question> getRandomQuestions() {
		return questionsApi.randomGet(10).getResults();
	}
	
}
