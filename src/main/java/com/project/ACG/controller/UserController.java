package com.project.ACG.controller;

import com.project.ACG.entity.User;
import com.project.ACG.repository.UserJpaRepository;
import com.project.ACG.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserJpaRepository userJpaRepository;
  private final UserService userService;

  @GetMapping("/user/list")
  public List<User> getUserList() {
    return userJpaRepository.findAllByStatusIsTrue().get();
  }

  @GetMapping("/user/list/csv")
  public ResponseEntity<byte[]> getUserListToCSV(HttpServletResponse response) throws IOException {
    return userService.getUserListToCSV(response);
  }


  @PostMapping("/user")
  public String deleteUser(@RequestParam String userId, @RequestParam String userEmail) {
    String targetUserId = userId.split(",")[0].trim();
    String targetUserEmail = userEmail.split(",")[0].trim();
    return userService.deleteUser(targetUserId, targetUserEmail);
  }
}
