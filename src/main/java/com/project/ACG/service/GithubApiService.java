package com.project.ACG.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ACG.entity.User;
import com.project.ACG.repository.UserJpaRepository;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class GithubApiService {

  @Value("${spring.security.oauth2.client.registration.github.client-id}")
  private String client_id;

  @Value("${spring.security.oauth2.client.registration.github.client-secret}")
  private String client_secret;

  private final UserJpaRepository userJpaRepository;

  public void getAccessToken(String code, RedirectAttributes redirectAttributes) throws IOException {
    URL url = new URL("https://github.com/login/oauth/access_token");

    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoInput(true);
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Accept", "application/json");
    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");

    try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
      bw.write("client_id=" + client_id + "&client_secret=" + client_secret + "&code=" + code);
      bw.flush();
    }

    int responseCode = conn.getResponseCode();

    String responseData = getResponse(conn, responseCode);

    conn.disconnect();

    access(responseData, redirectAttributes);
  }

  public void access(String response, RedirectAttributes redirectAttributes) throws IOException {

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> map = objectMapper.readValue(response, Map.class);
    String access_token = map.get("access_token");

    URL url = new URL("https://api.github.com/user");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Accept", "application/json");
    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
    conn.setRequestProperty("Authorization", "token " + access_token);

    int responseCode = conn.getResponseCode();

    String result = getResponse(conn, responseCode);

    conn.disconnect();

    try {
      JsonNode jsonNode = objectMapper.readTree(result);

      String login = jsonNode.get("login").asText();
      String name = jsonNode.get("name").asText();

      // 이전에 회원가입 전적이 있는 경우
      if(userJpaRepository.existsUserByUserIdAndUserName(login, name)){
        Optional<User> user = userJpaRepository.findUserByUserIdAndUserName(login, name);
        User existUser = user.get();
        // 활동 회원인 경우
        if (existUser.isStatus()) {
          existUser.updateToken(access_token);
          userJpaRepository.save(existUser);
        }
        // 휴면 계정인 경우
        else {
          existUser.updateToken(access_token);
          existUser.returnUser();
          userJpaRepository.save(existUser);
        }
      }
      // 처음 회원가입하는 경우
      else {
        User newUser = User.create(login, name, access_token);
        userJpaRepository.save(newUser);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    redirectAttributes.addFlashAttribute("result", result);
  }

  private String getResponse(HttpURLConnection conn, int responseCode) throws IOException {
    StringBuilder sb = new StringBuilder();
    if (responseCode == 200) {
      try (InputStream is = conn.getInputStream();
          BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
        for (String line = br.readLine(); line != null; line = br.readLine()) {
          sb.append(line);
        }
      }
    }
    return sb.toString();
  }

  public void success(HttpServletRequest request, Model model) throws JsonProcessingException {
    Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
    String response = null;
    if (inputFlashMap != null) {
      response = (String) inputFlashMap.get("result");
    }

    ObjectMapper objectMapper = new ObjectMapper();

    Map<String, String> result = objectMapper.readValue(response, Map.class);

    model.addAttribute("result", result);
  }
}