package com.darjan.quizapp.controllers;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.darjan.quizapp.models.Quiz;
import com.darjan.quizapp.models.dtos.AverageStatsDTO;
import com.darjan.quizapp.models.dtos.LeaderboardDTO;
import com.darjan.quizapp.models.dtos.PaginationResponseDTO;
import com.darjan.quizapp.models.dtos.QuestionApiDTO;
import com.darjan.quizapp.models.dtos.ResultChartItemDTO;
import com.darjan.quizapp.security.CustomUserDetails;
import com.darjan.quizapp.services.QuizService;
import com.darjan.quizapp.utils.Helper;
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
		return questionsApi.randomGet(10, 9, "easy", "base64").getResults();
	}

	@GetMapping("/")
	public Quiz getNewQuiz(@RequestParam(defaultValue = "9") int category,
			@RequestParam(defaultValue = "easy") String difficulty,
			@RequestParam(defaultValue = "10") int amount, @AuthenticationPrincipal CustomUserDetails user)
			throws Exception {
		return quizService.createNewQuiz(category, difficulty, amount, user);
	}

	@PostMapping("/")
	public void postSolvedQuiz(@RequestBody Quiz quiz, @AuthenticationPrincipal CustomUserDetails user) {
		quizService.handleQuizCompletion(quiz, user);
	}

	@GetMapping("/users/{userId}")
	public PaginationResponseDTO<Quiz> getQuizzesForUser(@PathVariable Long userId, @RequestParam int page,
			@RequestParam(name = "per_page") int perPage, @RequestParam(defaultValue = "id") String order,
			@RequestParam(defaultValue = "ASC", name = "order_by") String orderBy,
			@AuthenticationPrincipal CustomUserDetails user) {
		Pageable pageable;

		order = Helper.toCamelCase(order);
		if (orderBy.equalsIgnoreCase("DESC")) {
			pageable = PageRequest.of(page, perPage, Sort.by(order).descending());
		} else {
			pageable = PageRequest.of(page, perPage, Sort.by(order).ascending());
		}

		return quizService.findAllByUserId(user.getUser().getId(), pageable);
	}
	
	@GetMapping("/users/statistics/{userId}")
	public List<ResultChartItemDTO> getPieChartStatistics(@PathVariable Long userId) {
		return quizService.getPieChartStatistics(userId);
	}
	
	@GetMapping("/users/statistics/average/{userId}")
	public List<ResultChartItemDTO> getAveragePerCategory(@PathVariable Long userId) {
		return quizService.getAveragePerCategory(userId);
	}
	
	@GetMapping("/users/statistics/category/{userId}")
	public List<AverageStatsDTO> getAllAverageStatsPerCategory(@PathVariable Long userId) {
		return quizService.getAllAverageStatsPerCategory(userId);
	}
	
	@GetMapping("/users/statistics/leaderboard")
	public List<LeaderboardDTO> getLeaderboard() {
		return quizService.getLeaderboard();
	}
}
