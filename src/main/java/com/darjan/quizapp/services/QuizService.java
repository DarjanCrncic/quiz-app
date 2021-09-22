package com.darjan.quizapp.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.darjan.quizapp.models.Quiz;
import com.darjan.quizapp.models.dtos.AverageStatsDTO;
import com.darjan.quizapp.models.dtos.PaginationResponseDTO;
import com.darjan.quizapp.models.dtos.ResultChartItemDTO;
import com.darjan.quizapp.security.CustomOAuth2User;

public interface QuizService {

	public Quiz createNewQuiz(int category, String difficulty, int questionNumber, CustomOAuth2User user) throws Exception;

	public PaginationResponseDTO<Quiz> findAllByUserId(Long userId, Pageable pageable);

	public void handleQuizCompletion(Quiz quiz, CustomOAuth2User user);

	public Object findById(Long quizId);

	public List<ResultChartItemDTO> getPieChartStatistics(Long userId);

	public List<ResultChartItemDTO> getAveragePerCategory(Long userId);

	public List<AverageStatsDTO> getAllAverageStatsPerCategory(Long userId);
}
