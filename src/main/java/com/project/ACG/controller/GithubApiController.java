package com.project.ACG.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Controller
public class GithubApiController {

  @Value("${spring.security.oauth2.client.registration.github.client-id}")
  private String client_id;

  @Value("${spring.security.oauth2.client.registration.github.client-secret}")
  private String client_secret;

  @GetMapping("/success")
  public String success(HttpServletRequest request, Model model) throws JsonProcessingException {

    /**
     * Redirect의 Flash 값을 다루려면 RequestContextUtils를 사용해야 한다.
     **/
    Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
    String response = null;
    if (inputFlashMap != null) {
      response = (String) inputFlashMap.get("result");
    }

    ObjectMapper objectMapper = new ObjectMapper();

    Map<String, String> result = objectMapper.readValue(response, Map.class);

    model.addAttribute("result", result);
    return "success";
  }

  @GetMapping("/oauth2/authorization/github")
  public String getCode(@RequestParam String code, RedirectAttributes redirectAttributes) throws IOException {

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
    return "redirect:/success";
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
}
