package com.darjan.quizapp.models.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PaginationResponseDTO <T>{
	
	private List<T> rows = new ArrayList<>();
	private int total;
	private int pageCount;
}
