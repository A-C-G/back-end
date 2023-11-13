package com.project.ACG.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserDto {

	@Schema(type = "Boolean", example = "false")
	private boolean status;

	@Schema(type = "String", example = "2023-11-11-11-11")
	private String updateTime;

	@Schema(type = "String", example = "ACG_repo")
	private String repoName;

	public UserDto(boolean status, String updateTime, String repoName) {
		this.status = status;
		this.updateTime = updateTime;
		this.repoName = repoName;
	}

	public static UserDto create(boolean status, String updateTime, String repoName) {
		return new UserDto(status, updateTime, repoName);
	}
}
