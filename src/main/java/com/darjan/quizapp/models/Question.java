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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	
	@OneToOne(mappedBy = "question")
	@JsonManagedReference
	private CorrectAnswer correctAnswer;
	
	@OneToMany(mappedBy = "question")
	@JsonManagedReference
	private List<IncorrectAnswer> incorrectAnswers = new ArrayList<>();
	
	@OneToOne(mappedBy = "question")
	@JsonManagedReference
	private UserAnswer userAnswer;
	
	@ManyToOne
	@JoinColumn(name = "quiz_id", referencedColumnName = "id")
	@JsonBackReference
	private Quiz quiz;
}
