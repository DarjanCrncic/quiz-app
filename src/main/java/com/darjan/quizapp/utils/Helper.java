package com.darjan.quizapp.utils;

import java.util.Collections;
import java.util.List;

import com.darjan.quizapp.models.Answer;

public class Helper {
	
	public static void permute(List<Answer> list) {
		Collections.shuffle(list);
	}

}
