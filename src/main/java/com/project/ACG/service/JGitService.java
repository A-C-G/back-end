package com.project.ACG.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ACG.entity.User;
import com.project.ACG.repository.UserJpaRepository;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GHRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JGitService {

  private final UserJpaRepository userJpaRepository;

  public String createRepo(String userId, String userName, String repoName) throws JsonProcessingException {
    Optional<User> user = userJpaRepository.findUserByUserIdAndUserName(userId, userName);
    User existUser = user.get();
    String accessToken = existUser.getUserToken();

    try {
      GitHub github = GitHub.connectUsingOAuth(accessToken);
      GHCreateRepositoryBuilder builder = github.createRepository(repoName)
          .private_(false) // 필요에 따라 private 리포지토리로 설정
          .description("ACG Repository");
      GHRepository repository = builder.create();

      String IsSuccess = commitToGitHubRepository(existUser, repoName, accessToken, "initial commit");

      if (IsSuccess.equals("success")) {
        existUser.registerRepo(repoName);
        userJpaRepository.save(existUser);
        return "GitHub 리포지토리가 생성되었습니다.";
      } else {
        return IsSuccess;
      }
    } catch (IOException e) {
      e.printStackTrace();

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
        if (errorMessage.equals("name already exists on this account")){
          errorMessages += "\nError Message: 이미 존재하는 repository 이름입니다.";
        } else {
        errorMessages += "\nError Message: " + errorMessage;
        }
      }
      return "Message: " + message + errorMessages;
    }
  }

  private String commitToGitHubRepository(User user, String repoName, String accessToken, String commitMessage)
      throws IOException {
    String localRepoPath = "C:/ACG/ACG/users/" + user.getUserId(); // 로컬 저장소 경로 설정
    File localRepoDirectory = new File(localRepoPath);
    Git git = null;

    try {
      if (localRepoDirectory.exists()) {
        return "이미 서비스를 이용중 입니다.\n한 계정당 하나의 서비스만 이용 가능합니다.";
      }

      // GitHub 리포지토리를 로컬로 클론
      CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(accessToken, "");
      git = Git.cloneRepository()
          .setCredentialsProvider(credentialsProvider)
          .setURI("https://github.com/" + user.getUserId() + "/" + repoName + ".git")
          .setDirectory(localRepoDirectory)
          .call();

      // 변경사항을 만들고 초기 커밋
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.parseMediaType("text/csv"));
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
      String currentDateTime = LocalDateTime.now().format(formatter);
      File fileToCommit = new File(localRepoPath, "sample_" + currentDateTime + ".txt");
      fileToCommit.createNewFile();
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

      return "success";
    } catch (GitAPIException e) {
      // GitAPIException 예외 발생 시 오류 메시지 반환
      return e.getMessage();
    } catch (JGitInternalException ex) {
      return ex.getMessage();
    } finally {

    }
  }
}

