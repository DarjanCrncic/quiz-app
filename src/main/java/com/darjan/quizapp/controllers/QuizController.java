package com.darjan.quizapp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darjan.quizapp.models.QuestionApiDTO;
import com.darjan.quizapp.models.Quiz;
import com.darjan.quizapp.services.QuizService;
import com.darjan.quizapp.utils.QuestionsApi;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

	private QuestionsApi questionsApi;
	private QuizService quizService;

	@GetMapping("/test")
	public List<QuestionApiDTO> getRandomQuestions() {
		return questionsApi.randomGet(10).getResults();
	}

	@GetMapping("/")
	public Quiz getNewQuiz() {
		return quizService.createNewQuiz();
	}

}
