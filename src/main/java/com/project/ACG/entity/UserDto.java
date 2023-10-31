package com.project.ACG.entity;

import lombok.Getter;

@Getter
public class UserDto {

  private boolean status;
  private String updateTime;

  public UserDto (boolean status, String updateTime) {
    this.status = status;
    this.updateTime = updateTime;
  }

  public static UserDto create(boolean status, String updateTime) {
    return new UserDto(status, updateTime);
  }
}
