package com.project.ACG.controller;

import com.project.ACG.entity.User;
import com.project.ACG.repository.UserJpaRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserJpaRepository userJpaRepository;

  @GetMapping("/user/list")
  public List<User> getUserList() {
    return userJpaRepository.findAllByStatusIsTrue().get();
  }

  @PostMapping("/user")
  public String deleteUser(@RequestParam String userId, @RequestParam String userName) {
    String targetUserId = userId.split(",")[0].trim();
    String targetUserName = userName.split(",")[0].trim();
    Optional<User> user = userJpaRepository.findUserByUserIdAndUserName(targetUserId, targetUserName);
    User targetUser = user.get();
    if (targetUser == null) {
      return "존재하지 않는 유저입니다.";
    }
    if (targetUser.isStatus()) {
      targetUser.deleteUser();
      userJpaRepository.save(targetUser);
      return "유저 삭제 완료 : " + targetUser.getUserId();
    } else {
      return "이미 삭제된 계정입니다.";
    }
  }
}
