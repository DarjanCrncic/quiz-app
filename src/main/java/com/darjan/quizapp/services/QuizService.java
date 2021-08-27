package com.darjan.quizapp.services;

import org.springframework.data.domain.Pageable;

import com.darjan.quizapp.models.Quiz;
import com.darjan.quizapp.models.dtos.PaginationResponseDTO;

public interface QuizService {

	public Quiz createNewQuiz(int category, String difficulty, int questionNumber) throws Exception;

	public void handleQuizCompletion(Quiz quiz);

	public PaginationResponseDTO<Quiz> findAllByUserId(Long userId, Pageable pageable);
}
