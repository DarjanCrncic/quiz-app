package com.darjan.quizapp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping("/")
	public void postSolvedQuiz(@RequestBody Quiz quiz) {
		quizService.handleQuizCompletion(quiz);
	}
	
	@GetMapping("/users/{userId}")
	public List<Quiz> getQuizzesForUser(@PathVariable Long userId){
		return quizService.findAllByUserId(userId);
	}
}
