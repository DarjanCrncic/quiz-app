package com.darjan.quizapp.models;

import java.util.List;

import lombok.Data;

@Data
public class QuestionApiDTO {

	private String category;
	private String type;
	private String difficulty;
	private String question;
	
	private String correctAnswer;
	
	private List<String> incorrectAnswers;
}
