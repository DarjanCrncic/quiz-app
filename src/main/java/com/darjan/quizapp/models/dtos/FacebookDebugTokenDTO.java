package com.darjan.quizapp.models.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class FacebookDebugTokenDTO {

	public TokenData data;

	@Data
	public class TokenData {
		private String appId;
		private String type;
		private String application;
		private int dataAccessExpiresAt;
		private int expiresAt;
		
		@JsonAlias("is_valid")
		private boolean isValid;
		private String userId;
	}
}
