package com.darjan.quizapp.models.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class AuthRequest {
	
	@JsonAlias("accessToken")
	private String accessToken;
	@JsonAlias("userID")
	private String userID;
	@JsonAlias("signedRequest")
	private String signedRequest;
	private int dataAccessExpirationTime;
}
