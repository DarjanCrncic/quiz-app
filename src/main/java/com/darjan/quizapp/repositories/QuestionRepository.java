package com.darjan.quizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.darjan.quizapp.models.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
