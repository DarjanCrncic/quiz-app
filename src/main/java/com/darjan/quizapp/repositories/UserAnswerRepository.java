package com.darjan.quizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.darjan.quizapp.models.UserAnswer;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long>{

}
