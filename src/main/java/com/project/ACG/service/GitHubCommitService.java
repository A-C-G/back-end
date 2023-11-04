package com.project.ACG.service;

import com.project.ACG.entity.User;
import com.project.ACG.repository.UserJpaRepository;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
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

  @Scheduled(fixedRate = 12 * 60 * 60 * 1000) // 12시간마다 실행 (밀리초 단위)
  public void executeCommits() {
    List<User> userList = userJpaRepository.findAllByStatusIsTrue().get();
    for (User user : userList) {
      executorService.execute(() -> {
        try {
          // 각 사용자의 GitHub 리포지토리에 커밋을 실행하는 코드
          commitWithFileCleanup(user);
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
  public String commitWithFileCleanup(User user) throws IOException {
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

        // 현재 로컬 저장소 파일 수 세기
        File[] localRepoFiles = localRepoDirectory.listFiles();
        int fileCount = localRepoFiles != null ? localRepoFiles.length : 0;

        // 파일 수가 10개를 초과하는 경우 일부 파일 삭제
        if (fileCount > 10) {
          // 삭제할 파일 수 계산
          int filesToDelete = fileCount - 10;
          // 파일을 최신 수정일 기준으로 정렬
          Arrays.sort(localRepoFiles, Comparator.comparingLong(File::lastModified));

          // 파일 삭제
          for (int i = 0; i < filesToDelete; i++) {
            File fileToDelete = localRepoFiles[i];
            if (fileToDelete.delete()) {
              System.out.println("Deleted file: " + fileToDelete.getName());
            } else {
              System.out.println("Failed to delete file: " + fileToDelete.getName());
            }
          }

          // 변경사항을 GitHub 리포지토리로 커밋
          git.add()
              .addFilepattern(".")
              .call();
          git.commit()
              .setMessage("Auto Commit with File Cleanup")
              .call();
        }

        // 변경사항을 GitHub 리포지토리로 푸시
        git.push()
            .setCredentialsProvider(credentialsProvider)
            .call();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String currentDateTime = LocalDateTime.now().format(formatter);

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

