package com.project.ACG.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ACG.entity.User;
import com.project.ACG.repository.UserJpaRepository;
import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JGitService {

  private final UserJpaRepository userJpaRepository;

  /**
   * Repository를 생성하는 로직
   */
  public String createRepo(String userId, String userEmail, String repoName)
      throws JsonProcessingException {
    // 유저 검색
    Optional<User> user = userJpaRepository.findUserByUserIdAndUserEmail(userId, userEmail);
    User existUser = user.get();
    String accessToken = existUser.getUserToken();

    String localRepoPath = "/var/" + existUser.getUserId() + "/samples";
    File localRepoDirectory = new File(localRepoPath);

    // 로컬 디렉토리가 있는 경우
    if (localRepoDirectory.exists()) {
      return "이미 서비스를 이용중 입니다.\n한 계정당 하나의 서비스만 이용 가능합니다.";
    }

    // 이미 사용중인 저장소가 있는 경우
    if (existUser.getUserRepo() != null) {
      String existRepoName = userId + "/" + existUser.getUserRepo();
      try {
        GitHub github = GitHub.connectUsingOAuth(accessToken);
        GHRepository repository = github.getRepository(existRepoName);
        String IsSuccess = commitToGitHubRepository(existUser, existUser.getUserRepo(),
            "initial commit", localRepoPath,
            localRepoDirectory);
        if (IsSuccess.equals("success")) {
          return "이미 서비스를 이용중 입니다.\n한 계정당 하나의 서비스만 이용 가능합니다.";
        } else {
          return IsSuccess;
        }
      } catch (IOException e) {
        return e.getMessage();
      }
    }

    try {
      GitHub github = GitHub.connectUsingOAuth(accessToken); // 깃허브에 토큰을 통해 접근
      GHCreateRepositoryBuilder builder = github.createRepository(repoName) // 저장소 생성
          .private_(false) // 필요에 따라 private 리포지토리로 설정
          .description("ACG Repository") // 설명
          .homepage("https://prod.hyunn.site/description"); // URL 설정
      GHRepository repository = builder.create();

      // 깃허브 커밋 내용 작성
      String IsSuccess = commitToGitHubRepository(existUser, repoName, "initial commit",
          localRepoPath, localRepoDirectory);

      if (IsSuccess.equals("success")) {
        existUser.registerRepo(repoName);
        userJpaRepository.save(existUser);
        return "GitHub 리포지토리가 생성되었습니다.";
      } else {
        return IsSuccess;
      }
    } catch (IOException e) {
      e.printStackTrace();

      // 오류 처리
      String apiResponse = e.getMessage();
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(apiResponse);

      // "message" 필드 값 추출
      String message = jsonNode.get("message").asText();
      System.out.println("Message: " + message);

      // "errors" 배열에서 "message" 필드 값 추출
      JsonNode errorsNode = jsonNode.get("errors");
      String errorMessages = "";
      for (JsonNode error : errorsNode) {
        String errorMessage = error.get("message").asText();
        System.out.println("Error Message: " + errorMessage);
        if (errorMessage.equals("name already exists on this account")) {
          errorMessages += "\nError Message: 이미 존재하는 repository 이름입니다.";
        } else {
          errorMessages += "\nError Message: " + errorMessage;
        }
      }
      return "Message: " + message + errorMessages;
    }
  }

  /**
   * 초기 저장소 생성 후 커밋하는 로직
   */
  public String commitToGitHubRepository(User user, String repoName, String commitMessage,
      String localRepoPath, File localRepoDirectory)
      throws IOException {
    Git git = null;
    String accessToken = user.getUserToken();

    try {
      // GitHub 레포지토리 접속
      String userId = user.getUserId();
      String userToken = user.getUserToken();
      String userEmail = user.getUserEmail(); // 사용자별로 저장한 Git 사용자 이메일
      CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(
          userId, userToken);

      // 원격 저장소를 로컬로 복사한다.
      git = Git.cloneRepository()
          .setCredentialsProvider(credentialsProvider)
          .setURI("https://github.com/" + user.getUserId() + "/" + repoName + ".git")
          .setDirectory(localRepoDirectory)
          .call();

      // 변경사항을 만들고 초기 커밋
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.parseMediaType("text/csv"));
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
      ZoneId koreaZoneId = ZoneId.of("Asia/Seoul"); // 대한민국 시간대
      String currentDateTime = ZonedDateTime.now(koreaZoneId).format(formatter);
      File fileToCommit = new File(localRepoPath, "sample_" + currentDateTime);
      fileToCommit.createNewFile();

      // Git 사용자 이름과 이메일 설정
      git.getRepository().getConfig().setString("user", null, "name", userId);
      git.getRepository().getConfig().setString("user", null, "email", userEmail);
      git.getRepository().getConfig().save();

      git.add()
          .addFilepattern(".")
          .call();
      git.commit()
          .setMessage(commitMessage)
          .call();

      // 변경사항을 GitHub 리포지토리로 푸시
      git.push()
          .setCredentialsProvider(credentialsProvider)
          .call();
      user.updateAt(currentDateTime);
      userJpaRepository.save(user);
      return "success";
    } catch (GitAPIException e) {
      // GitAPIException 예외 발생 시 오류 메시지 반환
      return e.getMessage();
    } catch (JGitInternalException ex) {
      return ex.getMessage();
    }
	}

  /**
   * 유저 삭제
   */
  @Transactional
  public String deleteUser(String userId, String userEmail) {
    // 유저 탐색
    Optional<User> user = userJpaRepository.findUserByUserIdAndUserEmail(userId, userEmail);
    if (!user.isPresent()) {
      return "존재하지 않는 유저입니다.";
    }

    User targetUser = user.get();
    // 유저가 존재하는 경우 삭제
    if (targetUser.isStatus()) {
      targetUser.deleteUser();
      userJpaRepository.save(targetUser);

      // 유저 삭제 후 해당 로컬 디렉토리 및 파일 삭제
      String directoryPath = "/var/" + userId;
      File directory = new File(directoryPath);

      if (directory.exists()) {
        deleteDirectory(directory);
      }
      return "유저 삭제 완료 : " + targetUser.getUserId();
    } else {
      return "서비스 이용 중이 아닙니다.";
    }
  }

  /**
   * 디렉토리를 삭제하는 로직
   */
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
}

