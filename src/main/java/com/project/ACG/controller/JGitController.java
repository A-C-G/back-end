package com.project.ACG.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ACG.service.JGitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JGitController {

	private final JGitService jGitService;

	@PostMapping("/setting")
	public String createRepo(@RequestParam String userId, @RequestParam String userEmail,
		@RequestParam String repoName)
		throws JsonProcessingException {
		return jGitService.createRepo(userId, userEmail, repoName);
	}

	@PostMapping("/delete")
	public String deleteUser(@RequestParam String userId, @RequestParam String userEmail) {
		String targetUserId = userId.split(",")[0].trim();
		String targetUserEmail = userEmail.split(",")[0].trim();
		return jGitService.deleteUser(targetUserId, targetUserEmail);
	}
}
