package com.project.ACG.controller;

import com.project.ACG.entity.UserUpdateRequest;
import com.project.ACG.entity.UserUpdateResponse;
import com.project.ACG.exception.ErrorResponse;
import com.project.ACG.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user", description = "유저 Api")
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@Operation(summary = "유저 리스트를 csv 형식으로",
		description = "헤더의 토큰 값을 넣으면 모든 유저의 DB를 csv로 받을 수 있다.")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "모든 유저 리스트 반환 완료"),
		@ApiResponse(responseCode = "401", description = "토큰이 없거나 토큰이 정확하지 않습니다.",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{"
					+ "\"status\": \"UNAUTHORIZED\" ,"
					+ "\"msg\":\"토큰이 없거나 토큰이 정확하지 않습니다.\""
					+ "}")))})
	@Parameter(name = "Token", description = "token", schema = @Schema(type = "string"),
		in = ParameterIn.HEADER, example = "IAMTOKEN", required = true)
	@GetMapping("/user/list/csv")
	public ResponseEntity<byte[]> getUserListToCSV(@RequestHeader(value = "Token", required = false) String token) {
		return userService.getUserListToCSV(token);
	}

	@Operation(summary = "유저 정보 업데이트",
		description = "Json 형식의 유저 정보를 보내면 이를 DB에 직접적으로 반영한다.")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "유저 업데이트 완료"),
		@ApiResponse(responseCode = "401", description = "토큰이 없거나 토큰이 정확하지 않습니다.",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{"
					+ "\"status\": \"UNAUTHORIZED\" ,"
					+ "\"msg\":\"토큰이 없거나 토큰이 정확하지 않습니다.\""
					+ "}")))})
	@Parameter(name = "Token", description = "token", schema = @Schema(type = "string"),
		in = ParameterIn.HEADER, example = "IAMTOKEN", required = true)
	@PostMapping("/user/update")
	public ResponseEntity<UserUpdateResponse> updateUser(@RequestHeader(value = "Token", required = false) String token,
	@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
		return userService.updateUser(userUpdateRequest, token);
	}
}
