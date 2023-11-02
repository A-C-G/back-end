package com.project.ACG.service;

import com.project.ACG.entity.User;
import com.project.ACG.repository.UserJpaRepository;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class GitHubCommitService {
  private final JGitService jGitService;

  private final UserJpaRepository userJpaRepository;
  private final ExecutorService executorService = Executors.newFixedThreadPool(10); // 10개 thread 사용

  @Scheduled(fixedRate = 6 * 60 * 60 * 1000) // 6시간마다 실행 (밀리초 단위)
  public void executeCommits() {
    List<User> userList = userJpaRepository.findAllByStatusIsTrue().get();
    for (User user : userList) {
      executorService.execute(() -> {
        try {
          // 각 사용자의 GitHub 리포지토리에 커밋을 실행하는 코드
          commitToGitHubRepositoryByAllUsers(user);
        } catch (Exception e) {
          // 예외 처리 (예외 로깅 또는 다른 처리)
          e.printStackTrace();
        }
      });
    }
  }

  public String commitToGitHubRepositoryByAllUsers(User user) throws IOException {
    String localRepoPath = "/var/" + user.getUserId() + "/samples";
    File localRepoDirectory = new File(localRepoPath);
    Git git = null;

    if (!localRepoDirectory.exists()) {
      return jGitService.createRepo(user.getUserId(), user.getUserEmail(), user.getUserRepo());
    } else {
      try {
        // GitHub 레포지토리 접속
        CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(
            user.getUserToken(), "");
        git = Git.open(localRepoDirectory);

        // 변경사항을 만들고 초기 커밋
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String currentDateTime = LocalDateTime.now().format(formatter);
        File fileToCommit = new File(localRepoPath, "sample_" + currentDateTime + "_UTC.txt");
        fileToCommit.createNewFile();
        git.add()
            .addFilepattern(".")
            .call();
        git.commit()
            .setMessage("Auto Commit at " + currentDateTime)
            .call();

        // 변경사항을 GitHub 리포지토리로 푸시
        git.push()
            .setCredentialsProvider(credentialsProvider)
            .call();

        user.updateAt(currentDateTime);
        userJpaRepository.save(user);
        return "success";
      } catch (GitAPIException | IOException | JGitInternalException e) {
        // 예외 처리
        e.printStackTrace();
        return e.getMessage();
      } finally {

      }
    }
  }
}

