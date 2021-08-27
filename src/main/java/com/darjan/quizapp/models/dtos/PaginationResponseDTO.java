package com.darjan.quizapp.models.dtos;

import java.util.List;

import lombok.Data;

@Data
public class PaginationResponseDTO <T>{
	
	private List<T> rows;
	private int total;
	private int pageCount;
}
