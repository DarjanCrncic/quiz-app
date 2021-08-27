package com.darjan.quizapp.models.dtos;

import java.util.List;

import com.darjan.quizapp.utils.Base64Deserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
public class QuestionApiDTO {

	@JsonDeserialize(using = Base64Deserializer.class)
	private String category;
	
	@JsonDeserialize(using = Base64Deserializer.class)
	private String type;
	
	@JsonDeserialize(using = Base64Deserializer.class)
	private String difficulty;
	
	@JsonDeserialize(using = Base64Deserializer.class)
	private String question;
	
	@JsonDeserialize(using = Base64Deserializer.class)
	private String correctAnswer;
	
	@JsonDeserialize(contentUsing = Base64Deserializer.class)
	private List<String> incorrectAnswers;
}
