package com.darjan.quizapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.darjan.quizapp.models.Quiz;
import com.darjan.quizapp.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User getUserByEmail(String email);

	Optional<Quiz> findByUsername(String name);

}
