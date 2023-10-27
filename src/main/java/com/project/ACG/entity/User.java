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

  @Column(name = "user_token")
  private String userToken;

  @Column(name = "user_repo")
  private String userRepo;

  @Column(name = "status")
  private boolean status;

  public User(String userId, String userName, String userToken) {
    this.userId = userId;
    this.userName = userName;
    this.userToken = userToken;
    this.userRepo = null;
    this.status = true;
  }

  public static User create(String userId, String userName, String userToken) {
    return new User(userId, userName, userToken);
  }

  public void updateToken(String userToken) {
    this.userToken = userToken;
  }

  public void deleteUser() {
    this.status = false;
  }

  public void returnUser() {
    this.status = true;
  }

  public void registerRepo(String userRepo) {
    this.userRepo = userRepo;
  }
}
