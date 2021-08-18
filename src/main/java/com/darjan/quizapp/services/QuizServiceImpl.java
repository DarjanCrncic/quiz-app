package com.darjan.quizapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.darjan.quizapp.models.Answer;
import com.darjan.quizapp.models.Question;
import com.darjan.quizapp.models.QuestionApiDTO;
import com.darjan.quizapp.models.Quiz;
import com.darjan.quizapp.models.User;
import com.darjan.quizapp.repositories.AnswerRepository;
import com.darjan.quizapp.repositories.QuestionRepository;
import com.darjan.quizapp.repositories.QuizRepository;
import com.darjan.quizapp.repositories.UserRepository;
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

	@Override
	public Quiz createNewQuiz() {
		List<QuestionApiDTO> questionList = questionsApi.randomGet(10).getResults();

		Quiz quiz = new Quiz();
		quizRepository.save(quiz);
		
		User user = userRepository.findById(1L).orElse(null);
		quiz.setUser(user);
		user.getQuizzes().add(quiz);

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

	@Override
	public void handleQuizCompletion(Quiz quiz) {
		Quiz savedQuiz = quizRepository.findById(quiz.getId()).orElse(null);
		
		if (savedQuiz != null) {
			List<Question> savedQuestions = savedQuiz.getQuestions();
			
			for (int i=0; i<savedQuestions.size(); i++) {
				Question savedQuestion = savedQuestions.get(i);
				savedQuestion.setUserAnswer(quiz.getQuestions().get(i).getUserAnswer());
				questionRepository.save(savedQuestion);
			}
			quizRepository.save(savedQuiz);
		}
	}

	@Override
	public List<Quiz> findAllByUserId(Long userId) {
		return quizRepository.findAllByUserId(userId);
	}
}
