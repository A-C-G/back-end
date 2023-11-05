package com.project.ACG.entity;

import lombok.Getter;

@Getter
public class UserDto {

	private boolean status;
	private String updateTime;
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
