package com.darjan.quizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.darjan.quizapp.models.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long>{
	
}
