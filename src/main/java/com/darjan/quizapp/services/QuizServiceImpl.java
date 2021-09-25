package com.darjan.quizapp.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.darjan.quizapp.models.Answer;
import com.darjan.quizapp.models.Question;
import com.darjan.quizapp.models.Quiz;
import com.darjan.quizapp.models.QuizDifficulty;
import com.darjan.quizapp.models.User;
import com.darjan.quizapp.models.dtos.AverageStatsDTO;
import com.darjan.quizapp.models.dtos.LeaderboardDTO;
import com.darjan.quizapp.models.dtos.PaginationResponseDTO;
import com.darjan.quizapp.models.dtos.QuestionApiDTO;
import com.darjan.quizapp.models.dtos.ResultChartItemDTO;
import com.darjan.quizapp.repositories.AnswerRepository;
import com.darjan.quizapp.repositories.QuestionRepository;
import com.darjan.quizapp.repositories.QuizRepository;
import com.darjan.quizapp.repositories.UserRepository;
import com.darjan.quizapp.security.CustomOAuth2User;
import com.darjan.quizapp.utils.Helper;
import com.darjan.quizapp.utils.QuestionsApi;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {
	
	private QuestionsApi questionsApi;
	private QuizRepository quizRepository;
	private QuestionRepository questionRepository;
	private AnswerRepository answerRepository;
	private UserRepository userRepository;
	
	private static final int categoryMinId = 9;
	private static final int categoryMaxId = 32;
	private static final int minQuestionNum = 10;
	private static final int maxQuestionNum = 50;

	@Override
	public Quiz createNewQuiz(int category, String difficulty, int questionNumber, CustomOAuth2User auth2User) throws Exception {
		List<QuestionApiDTO> questionList = null;
		if (isQuizRequestValid(difficulty, category, questionNumber)){
			questionList = questionsApi.randomGet(questionNumber, category, difficulty, "base64").getResults();
			
			if (questionList.isEmpty()) {
				questionList = questionsApi.randomGet(questionNumber, category, "easy", "base64").getResults();
			}
		} else {
			throw new Exception("Request not valid.");
		}
		
		Quiz quiz = new Quiz();
		quizRepository.save(quiz);
		
		User user = userRepository.findById(auth2User.getId()).orElse(null);
		quiz.setUser(user);
		user.getQuizzes().add(quiz);
		quiz.setEnabled(true);
		
		quiz.setCategory(questionList.get(0).getCategory());
		quiz.setDifficulty(questionList.get(0).getDifficulty());

		for (QuestionApiDTO question : questionList) {
			Question newQuestion = new Question();

			newQuestion.setQuiz(quiz);
			fillQuestionMetaData(newQuestion, question);
			Question savedQuestion = questionRepository.save(newQuestion);

			Answer answer = new Answer();
			answer.setAnswer(question.getCorrectAnswer());
			answer.setQuestion(savedQuestion);
			Answer savedAnswer = answerRepository.save(answer);
			savedQuestion.setCorrectAnswerId(savedAnswer.getId());
			savedQuestion.getAnswers().add(savedAnswer);

			for (String inAnswer : question.getIncorrectAnswers()) {
				answer = new Answer();
				answer.setAnswer(inAnswer);
				answer.setQuestion(savedQuestion);
				savedAnswer = answerRepository.save(answer);
				savedQuestion.getAnswers().add(savedAnswer);
			}
			savedQuestion.setUserAnswer(savedAnswer.getId());
			savedQuestion.setQuiz(quiz);
			savedQuestion = questionRepository.save(newQuestion);
			quiz.getQuestions().add(savedQuestion);
		}
		for (Question question : quiz.getQuestions()) {
			Helper.permute(question.getAnswers());
		}

		return quiz;
	}
	
	private void fillQuestionMetaData(Question newQuestion, QuestionApiDTO questionData) {
		newQuestion.setCategory(questionData.getCategory());
		newQuestion.setDifficulty(questionData.getDifficulty());
		newQuestion.setQuestion(questionData.getQuestion());
		newQuestion.setType(questionData.getType());
	}
	
	private boolean isQuizRequestValid(String difficulty, int category, int questionNumber) {
		return category >= categoryMinId && category <= categoryMaxId
				&& (difficulty.equals(QuizDifficulty.easy.toString()) || difficulty.equals(QuizDifficulty.medium.toString())
						|| difficulty.equals(QuizDifficulty.hard.toString()))
				&& questionNumber >= minQuestionNum && questionNumber <= maxQuestionNum;
	}

	@Override
	public void handleQuizCompletion(Quiz quiz, CustomOAuth2User auth2User) {
		Quiz savedQuiz = quizRepository.findById(quiz.getId()).orElse(null);
		User user = userRepository.findById(auth2User.getId()).orElse(null);
		int correct = 0;
		
		if (savedQuiz != null && user != null) {
			List<Question> savedQuestions = savedQuiz.getQuestions();
			savedQuiz.setEnabled(false);
			savedQuiz.setUser(user);
			
			for (int i=0; i<savedQuestions.size(); i++) {
				Question savedQuestion = savedQuestions.get(i);
				
				long userAnswer = quiz.getQuestions().get(i).getUserAnswer();
				savedQuestion.setUserAnswer(userAnswer);
				questionRepository.save(savedQuestion);
				
				if (userAnswer == savedQuestion.getCorrectAnswerId()) {
					correct++;
				}
			}
			savedQuiz.setResult((double) correct/savedQuestions.size());
			quizRepository.save(savedQuiz);
		}
	}

	@Override
	public PaginationResponseDTO<Quiz> findAllByUserId(Long userId, Pageable pageable) {
		Page<Quiz> page = quizRepository.findAllByUserId(userId, pageable);
		PaginationResponseDTO<Quiz> dto = new PaginationResponseDTO<>();
		if (page.hasContent()) dto.setRows(page.getContent());
		dto.setPageCount(page.getTotalPages());
		dto.setTotal(Math.toIntExact(page.getTotalElements()));
		return dto;
	}

	@Override
	public Object findById(Long quizId) {
		return quizRepository.findById(quizId);
	}

	@Override
	public List<ResultChartItemDTO> getPieChartStatistics(Long userId) {
		return quizRepository.getPieChartStats(userId);
	}

	@Override
	public List<ResultChartItemDTO> getAveragePerCategory(Long userId) {
		return quizRepository.getAverageQuizResultByCategory(userId);
	}

	@Override
	public List<AverageStatsDTO> getAllAverageStatsPerCategory(Long userId) {
		return quizRepository.getAverageStats(userId);
	}

	@Override
	public List<LeaderboardDTO> getLeaderboard() {
		return quizRepository.getLeaderboard();
	}
}
