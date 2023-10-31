package com.project.ACG.service;

import com.project.ACG.entity.User;
import com.project.ACG.entity.UserDto;
import com.project.ACG.repository.UserJpaRepository;
import java.io.File;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserJpaRepository userJpaRepository;

  @Transactional
  public String deleteUser(String userId, String userName) {
    Optional<User> user = userJpaRepository.findUserByUserIdAndUserName(userId, userName);
    User targetUser = user.get();
    if (targetUser.isStatus()) {
      targetUser.deleteUser();
      userJpaRepository.save(targetUser);

      // 유저 삭제 후 해당 디렉토리 및 파일 삭제
      String directoryPath = "C:/ACG/ACG/users/" + userId;
      File directory = new File(directoryPath);

      if (directory.exists()) {
        deleteDirectory(directory);
      }

      return "유저 삭제 완료 : " + targetUser.getUserId();
    } else {
      return "없는 계정입니다.";
    }
  }

  private void deleteDirectory(File directory) {
    if (directory.isDirectory()) {
      File[] files = directory.listFiles();
      if (files != null) {
        for (File file : files) {
          deleteDirectory(file);
        }
      }
    }
    directory.delete();
  }

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

  public UserDto userInfo(String userId, String userName) {
    Optional<User> user = userJpaRepository.findUserByUserIdAndUserName(userId, userName);

    if (!userJpaRepository.existsUserByUserIdAndUserName(userId, userName)) {
      return UserDto.create(false, null);
    }
    User targetUser = user.get();

    boolean status = false;
    if (targetUser.isStatus()) {
      status = true;
    }

    String updateTime = null;
    if (targetUser.getUpdateTime() != null) {
      updateTime = targetUser.getUpdateTime();
    }

    return UserDto.create(status, updateTime);
  }
}
