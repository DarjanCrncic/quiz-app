package com.darjan.quizapp.services;

import org.springframework.data.domain.Pageable;

import com.darjan.quizapp.models.Quiz;
import com.darjan.quizapp.models.dtos.PaginationResponseDTO;
import com.darjan.quizapp.security.CustomOAuth2User;

public interface QuizService {

	public Quiz createNewQuiz(int category, String difficulty, int questionNumber, CustomOAuth2User user) throws Exception;

	public PaginationResponseDTO<Quiz> findAllByUserId(Long userId, Pageable pageable);

	public void handleQuizCompletion(Quiz quiz, CustomOAuth2User user);
}
