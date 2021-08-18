package com.darjan.quizapp.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String category;
	private String type;
	private String difficulty;
	private String question;
	
	private Long correctAnswerId;
	
	@OneToMany(mappedBy = "question")
	private List<Answer> answers = new ArrayList<>();
	
	private Long userAnswer;
	
	@ManyToOne
	@JoinColumn(name = "quiz_id", referencedColumnName = "id")
	@JsonIgnore
	private Quiz quiz;

	@Override
	public String toString() {
		return "Question [id=" + id + ", category=" + category + ", type=" + type + ", difficulty=" + difficulty
				+ ", question=" + question + ", correctAnswerId=" + correctAnswerId + ", answers=" + answers
				+ ", userAnswer=" + userAnswer + "]";
	}
	
	
}
