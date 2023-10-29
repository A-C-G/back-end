package com.project.ACG.controller;

import com.project.ACG.entity.User;
import com.project.ACG.repository.UserJpaRepository;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

  @GetMapping("/user/list/csv")
  public ResponseEntity<byte[]> getUserListToCSV(HttpServletResponse response) throws IOException {
    // CSV 파일의 내용을 문자열로 생성
    StringBuilder csvData = new StringBuilder();

    // CSV 헤더 행 생성
    csvData.append("ID,user_ID,user_Name,user_Token,user_Repo,status\n");

    // 사용자 목록을 가져오고 CSV 데이터를 추가
    List<User> userList = userJpaRepository.findAllByStatusIsTrue().get();
    for (User user : userList) {
      csvData.append(user.getId()).append(",")
          .append(user.getUserId()).append(",")
          .append(user.getUserName()).append(",")
          .append(user.getUserToken()).append(",")
          .append(user.getUserRepo()).append(",")
          .append(user.isStatus()).append("\n");
    }

    // CSV 문자열을 바이트 배열로 변환
    byte[] csvBytes = csvData.toString().getBytes(StandardCharsets.UTF_8);

    // CSV 파일을 HTTP 응답으로 반환
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType("text/csv"));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
    String currentDateTime = LocalDateTime.now().format(formatter);
    headers.setContentDispositionFormData("attachment", "users_" + currentDateTime + ".csv");

    return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
  }


  @PostMapping("/user")
  public String deleteUser(@RequestParam String userId, @RequestParam String userName) {
    String targetUserId = userId.split(",")[0].trim();
    String targetUserName = userName.split(",")[0].trim();
    Optional<User> user = userJpaRepository.findUserByUserIdAndUserName(targetUserId, targetUserName);
    User targetUser = user.get();
    if (targetUser.isStatus()) {
      targetUser.deleteUser();
      userJpaRepository.save(targetUser);
      return "유저 삭제 완료 : " + targetUser.getUserId();
    } else {
      return "이미 삭제된 계정입니다.";
    }
  }
}
