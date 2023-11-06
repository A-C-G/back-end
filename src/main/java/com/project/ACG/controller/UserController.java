package com.project.ACG.controller;

import com.project.ACG.entity.dto.UserUpdateRequest;
import com.project.ACG.entity.dto.UserUpdateResponse;
import com.project.ACG.repository.UserJpaRepository;
import com.project.ACG.service.UserService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserJpaRepository userJpaRepository;

	@GetMapping("/user/list/csv")
	public ResponseEntity<byte[]> getUserListToCSV(HttpServletResponse response,
		@RequestHeader(value = "Token", required = false) String token) throws IOException {
		return userService.getUserListToCSV(response, token);
	}

	@PostMapping("/user")
	public String deleteUser(@RequestParam String userId, @RequestParam String userEmail) {
		String targetUserId = userId.split(",")[0].trim();
		String targetUserEmail = userEmail.split(",")[0].trim();
		return userService.deleteUser(targetUserId, targetUserEmail);
	}

	@PostMapping("/user/update")
	public ResponseEntity<UserUpdateResponse> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
		return userService.updateUser(userUpdateRequest);
	}
}
