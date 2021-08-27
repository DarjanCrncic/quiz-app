package com.darjan.quizapp.models.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseObject {
	
	private int responseCode;
	private List<QuestionApiDTO> results;
}
