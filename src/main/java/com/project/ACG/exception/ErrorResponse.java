package com.project.ACG.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

	@Schema(type = "HttpStatus", example = "오류 상태")
	private final HttpStatus status;

	@Schema(type = "String", example = "오류에 대한 간단한 메세지")
	private final String msg;

	public ErrorResponse(HttpStatus status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public static ErrorResponse create(HttpStatus status, String msg) {
		return new ErrorResponse(status, msg);
	}
}
