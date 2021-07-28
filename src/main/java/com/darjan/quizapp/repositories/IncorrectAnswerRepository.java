package com.darjan.quizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.darjan.quizapp.models.IncorrectAnswer;

public interface IncorrectAnswerRepository extends JpaRepository<IncorrectAnswer, Long>{
	
}
