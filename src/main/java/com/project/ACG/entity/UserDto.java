package com.project.ACG.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserDto {

  @Schema(type = "Boolean", example = "false")
  private final boolean status;

  @Schema(type = "String", example = "2023-11-11-11-11")
  private final String updateTime;

  @Schema(type = "String", example = "ACG_repo")
  private final String repoName;

  public UserDto(boolean status, String updateTime, String repoName) {
    this.status = status;
    this.updateTime = updateTime;
    this.repoName = repoName;
  }

  public static UserDto create(boolean status, String updateTime, String repoName) {
    return new UserDto(status, updateTime, repoName);
  }
}
