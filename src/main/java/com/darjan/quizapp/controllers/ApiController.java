package com.darjan.quizapp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darjan.quizapp.models.CorrectAnswer;
import com.darjan.quizapp.models.IncorrectAnswer;
import com.darjan.quizapp.models.Question;
import com.darjan.quizapp.models.QuestionApiDTO;
import com.darjan.quizapp.models.Quiz;
import com.darjan.quizapp.repositories.CorrectAnswerRepository;
import com.darjan.quizapp.repositories.IncorrectAnswerRepository;
import com.darjan.quizapp.repositories.QuestionRepository;
import com.darjan.quizapp.repositories.QuizRepository;
import com.darjan.quizapp.utils.QuestionsApi;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ApiController {

	private QuestionsApi questionsApi;
	private QuizRepository quizRepository;
	private QuestionRepository questionRepository;
	private CorrectAnswerRepository correctAnswerRepository;
	private IncorrectAnswerRepository incorrectAnswerRepository;
	
	@GetMapping("/test")
	public List<QuestionApiDTO> getRandomQuestions() {
		return questionsApi.randomGet(10).getResults();
	}
	
	@GetMapping("/new")
	public Quiz getNewQuiz() {
		List<QuestionApiDTO> questionList = questionsApi.randomGet(10).getResults();
		
		Quiz quiz = new Quiz();
		quizRepository.save(quiz);
		
		for(QuestionApiDTO question : questionList) {
			Question newQuestion = new Question();

			newQuestion.setQuiz(quiz);
			fillQuestionMetaData(newQuestion, question);
			Question savedQuestion = questionRepository.save(newQuestion);
			
			CorrectAnswer correctAnswer = new CorrectAnswer();
			correctAnswer.setAnswer(question.getCorrectAnswer());
			correctAnswer.setQuestion(savedQuestion);
			CorrectAnswer savedCorrectAnswer = correctAnswerRepository.save(correctAnswer);
			savedQuestion.setCorrectAnswer(savedCorrectAnswer);
			
			for(String incorrectAnswer : question.getIncorrectAnswers()) {
				IncorrectAnswer newIncorrectAnswer = new IncorrectAnswer();
				newIncorrectAnswer.setAnswer(incorrectAnswer);
				newIncorrectAnswer.setQuestion(savedQuestion);
				IncorrectAnswer savedIncorrectAnswer = incorrectAnswerRepository.save(newIncorrectAnswer);
				savedQuestion.getIncorrectAnswers().add(savedIncorrectAnswer);
			}
			
			savedQuestion = questionRepository.save(newQuestion);
			quiz.getQuestions().add(savedQuestion);
		}
		
		return quiz;
	}
	
	private void fillQuestionMetaData(Question newQuestion, QuestionApiDTO questionData) {
		newQuestion.setCategory(questionData.getCategory());
		newQuestion.setDifficulty(questionData.getDifficulty());
		newQuestion.setQuestion(questionData.getQuestion());
		newQuestion.setType(questionData.getType());
	}
		
}
