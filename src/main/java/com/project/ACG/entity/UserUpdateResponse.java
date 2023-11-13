package com.project.ACG.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserUpdateResponse {

	@Schema(type = "Long", example = "1")
	private Long id;

	@Schema(type = "String", example = "tester")
	private String userId;

	@Schema(type = "String", example = "test@example.com")
	private String userEmail;

	@Schema(type = "String", example = "2023-11-11-11-11")
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
