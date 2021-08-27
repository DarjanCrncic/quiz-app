package com.darjan.quizapp.utils;

import java.util.Collections;
import java.util.List;

import com.darjan.quizapp.models.Answer;

public class Helper {

	public static void permute(List<Answer> list) {
		Collections.shuffle(list);
	}

	public static String toCamelCase(String phrase) {
		while (phrase.contains("_")) {
			phrase = phrase.replaceFirst("_[a-z]",
					String.valueOf(Character.toUpperCase(phrase.charAt(phrase.indexOf("_") + 1))));
		}
		return phrase;
	}
}
