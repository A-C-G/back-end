package com.project.ACG.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "user_name")
  private String userName;

  @Column(name = "user_email")
  private String userEmail;

  @Column(name = "user_token")
  private String userToken;

  @Column(name = "user_repo")
  private String userRepo;

  @Column(name = "status")
  private boolean status;

  @Column(name = "update_time")
  private String updateTime;

  public User(String userId, String userName, String userEmail, String userToken) {
    this.userId = userId;
    this.userName = userName;
    this.userEmail = userEmail;
    this.userToken = userToken;
    this.userRepo = null;
    this.status = false;
    this.updateTime = null;
  }

  public static User create(String userId, String userName, String userEmail, String userToken) {
    return new User(userId, userName, userEmail, userToken);
  }

  public void updateToken(String userToken) {
    this.userToken = userToken;
  }

  public void returnUser(String userToken) {
    this.userToken = userToken;
    this.userRepo = null;
    this.status = false;
    this.updateTime = null;
  }

  public void deleteUser() {
    this.status = false;
  }

  public void registerRepo(String userRepo) {
    this.userRepo = userRepo;
    this.status = true;
  }

  public void updateAt(String dateTime) {
    this.updateTime = dateTime;
  }

  public void updateName(String userName) {
    this.userName = userName;
  }
}
