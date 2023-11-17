package com.project.ACG.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ACG.entity.UserDto;
import com.project.ACG.service.GithubApiService;
import com.project.ACG.service.UserService;
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
	private final UserService userService;

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
	public String getCode(@RequestParam(required = false) String code,
		@RequestParam(required = false) String error, RedirectAttributes redirectAttributes)
		throws IOException {
		if ("access_denied".equals(error)) {
			System.out.println("승인을 취소하셨습니다.");
			return "redirect:/";
		}

		githubApiService.getAccessToken(code, redirectAttributes);
		return "redirect:/success";
	}

	@GetMapping("/user")
	public String userInfo(@RequestParam String userId, @RequestParam String userEmail,
		@RequestParam String userName, Model model) {
		UserDto userDto = userService.userInfo(userId, userEmail, userName);
		model.addAttribute("info", userDto);
		return "info";
	}
}
