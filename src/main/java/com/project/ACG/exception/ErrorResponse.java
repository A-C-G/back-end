package com.project.ACG.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

	private final HttpStatus status;

	private final String msg;

	public ErrorResponse(HttpStatus status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public static ErrorResponse create(HttpStatus status, String msg) {
		return new ErrorResponse(status, msg);
	}
}
