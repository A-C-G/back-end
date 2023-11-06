package com.project.ACG.entity.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

	@Digits(integer = 10, fraction = 0, message = "id는 양의 정수값만 입력하세요.")
	@NotNull(message = "id를 입력하세요.")
	private Double id;

	@NotBlank(message = "userId를 입력하세요.")
	private String userId;

	@Email(message = "올바른 이메일 형식을 입력하세요.")
	@NotBlank(message = "userEmail을 입력하세요.")
	private String userEmail;

	@NotBlank(message = "updateTime을 입력하세요.")
	private String updateTime;

	private String error;

	public UserUpdateRequest(Double id, String userId, String userEmail, String updateTime, String error) {
		this.id = id;
		this.userId = userId;
		this.userEmail = userEmail;
		this.updateTime = updateTime;
		this.error = error;
	}
}
