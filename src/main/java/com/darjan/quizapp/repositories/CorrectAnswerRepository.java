package com.darjan.quizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.darjan.quizapp.models.CorrectAnswer;

public interface CorrectAnswerRepository extends JpaRepository<CorrectAnswer, Long> {

}
