package com.darjan.quizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.darjan.quizapp.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
