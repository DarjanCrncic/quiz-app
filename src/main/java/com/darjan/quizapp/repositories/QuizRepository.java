package com.darjan.quizapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.darjan.quizapp.models.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long>{

	Page<Quiz> findAllByUserId(Long userId, Pageable pageable);

}
