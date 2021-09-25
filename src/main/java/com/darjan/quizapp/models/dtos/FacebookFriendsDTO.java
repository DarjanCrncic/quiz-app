package com.darjan.quizapp.models.dtos;

import java.util.List;

import lombok.Data;

@Data
public class FacebookFriendsDTO {

	private List<FacebookUser> data;

	@Data
	public static class FacebookUser {
		private String name;
		private String id;
		private FacebookPicture picture;
		private	double averageScore;
		private Long dbId;

		@Data public static class FacebookPicture {
			private FacebookPictureData data;

			@Data
			public static class FacebookPictureData {
				private int height;
				private boolean isSilhouette;
				private String url;
				private int width;
			}
		}
	}
}
