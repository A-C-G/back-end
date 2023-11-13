package com.project.ACG.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

	@Schema(type = "Long", example = "1")
	@Digits(integer = 10, fraction = 0, message = "id는 양의 정수값만 입력하세요.")
	@NotNull(message = "id를 입력하세요.")
	private Double id;

	@Schema(type = "String", example = "tester")
	@NotBlank(message = "userId를 입력하세요.")
	private String userId;

	@Schema(type = "String", example = "test@example.com")
	@Email(message = "올바른 이메일 형식을 입력하세요.")
	@NotBlank(message = "userEmail을 입력하세요.")
	private String userEmail;

	@Schema(type = "String", example = "2023-11-11-11-11")
	@NotBlank(message = "updateTime을 입력하세요.")
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}-\\d{2}-\\d{2}-\\d{2}$", message = "yyyy-MM-dd-HH-mm-ss 형식으로 입력하세요.")
	private String updateTime;

	@Schema(type = "String", example = "오류에 대한 메세지")
	private String error;

	public UserUpdateRequest(Double id, String userId, String userEmail, String updateTime, String error) {
		this.id = id;
		this.userId = userId;
		this.userEmail = userEmail;
		this.updateTime = updateTime;
		this.error = error;
	}
}
