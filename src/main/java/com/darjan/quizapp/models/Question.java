package com.darjan.quizapp.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
	
	private String category;
	private String type;
	private String difficulty;
	private String question;
	@JsonProperty("correct_answer")
	private String correctAnswer;
	@JsonProperty("incorrect_answers")	
	private List<String> incorrectAnswers;
}
