package com.darjan.quizapp.services;

import java.util.List;

import com.darjan.quizapp.models.Quiz;

public interface QuizService {

	public Quiz createNewQuiz();

	public void handleQuizCompletion(Quiz quiz);

	public List<Quiz> findAllByUserId(Long userId);
}
