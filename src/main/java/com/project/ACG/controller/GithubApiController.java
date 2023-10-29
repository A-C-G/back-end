package com.project.ACG.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ACG.service.GithubApiService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
public class GithubApiController {

  private final GithubApiService githubApiService;

  @GetMapping("/success")
  public String success(HttpServletRequest request, Model model) throws JsonProcessingException {
    try {
      githubApiService.success(request, model);
    } catch (IllegalArgumentException e) {
      return "redirect:/";
    }

    return "success";
  }


  @GetMapping("/oauth2/authorization/github")
  public String getCode(@RequestParam String code, @RequestParam(required = false) String error, RedirectAttributes redirectAttributes)
      throws IOException {
    if ("access_denied".equals(error)) {
      System.out.println("승인을 취소하셨습니다.");
      return "redirect:/login";
    }

    githubApiService.getAccessToken(code, redirectAttributes);
    return "redirect:/success";
  }

  @GetMapping("/setting")
  public String setting() {
    return "setting";
  }

  @GetMapping("/withdraw")
  public String withdraw() {
    return "withdraw";
  }

}
