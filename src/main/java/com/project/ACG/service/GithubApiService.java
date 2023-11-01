package com.project.ACG.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

  // 엑세스 토큰을 받아오는 로직
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

  // 엑세스 토큰으로 User 객체를 생성하는 로직
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

    String results = getResponse(conn, responseCode);

    String email = getEmail(response, redirectAttributes);

    ObjectNode rootNode = (ObjectNode) objectMapper.readTree(results);
    rootNode.put("Email", email);
    String result = objectMapper.writeValueAsString(rootNode);

    conn.disconnect();

    // 정보를 받아와서 필요한 정보만 파싱 후 User객체 생성
    try {
      JsonNode jsonNode = objectMapper.readTree(result);

      String login = jsonNode.get("login").asText();
      String name = jsonNode.get("name").asText();
      email = jsonNode.get("Email").asText();

      // 이전에 회원가입 전적이 있는 경우
      if(userJpaRepository.existsUserByUserIdAndUserEmail(login, email)){
        Optional<User> user = userJpaRepository.findUserByUserIdAndUserEmail(login, email);
        User existUser = user.get();

        existUser.updateToken(access_token);
        userJpaRepository.save(existUser);
      }
      // 처음 회원가입하는 경우
      else {
        User newUser = User.create(login, name, email, access_token);
        userJpaRepository.save(newUser);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    redirectAttributes.addFlashAttribute("result", result);
  }

  // 200 요청이 왔을때, 데이터를 문자열화
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

  public String getEmail(String response, RedirectAttributes redirectAttributes) throws IOException {

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> map = objectMapper.readValue(response, Map.class);
    String access_token = map.get("access_token");

    URL url = new URL("https://api.github.com/user/emails");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Accept", "application/json");
    conn.setRequestProperty("User-Agent",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
    conn.setRequestProperty("Authorization", "token " + access_token);

    int responseCode = conn.getResponseCode();

    String result_email = getResponse(conn, responseCode);

    JsonNode jsonNode = objectMapper.readTree(result_email);

    String email = jsonNode.get(0).get("email").asText();

    conn.disconnect();

    return email;
  }
}
