package com.project.ACG.entity.dto;

import lombok.Getter;

@Getter
public class UserUpdateResponse {
	private Long id;
	private String userId;
	private String userEmail;
	private String message;

	public UserUpdateResponse(Long id, String userId, String userEmail, String message) {
		this.id = id;
		this.userId = userId;
		this.userEmail = userEmail;
		this.message = message;
	}

	public static UserUpdateResponse create(Long id, String userId, String userEmail, String message) {
		return new UserUpdateResponse(id, userId, userEmail, message);
	}

}
