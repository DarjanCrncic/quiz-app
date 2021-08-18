package com.darjan.quizapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.darjan.quizapp.models.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long>{

	List<Quiz> findAllByUserId(Long userId);

}
