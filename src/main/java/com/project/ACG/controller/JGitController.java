package com.project.ACG.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ACG.service.JGitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "jgit", description = "JGit Api")
@RestController
@RequiredArgsConstructor
public class JGitController {

	private final JGitService jGitService;

	@Operation(summary = "깃허브 저장소 생성 후 연결",
		description = "ID와 Email, 저장소 이름을 받아서 깃허브 저장소를 생성 후 로컬 파일과 연결, 커밋, 푸시한다.")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "깃허브 저장소 생성 완료",
		content = @Content(mediaType = "string",
		examples = @ExampleObject(value = "GitHub 리포지토리가 생성되었습니다.")))})
	@PostMapping("/user/setting")
	public String createRepo(@RequestParam String userId, @RequestParam String userEmail,
		@RequestParam String repoName)
		throws JsonProcessingException {
		return jGitService.createRepo(userId, userEmail, repoName);
	}
}
