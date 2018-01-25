package com.scholarum.service;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.scholarum.common.exception.ApiErrorResponse;
import com.scholarum.common.exception.ScException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = { ScException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrorResponse paycrException(ScException ex) {
		logger.error(ex.getMessage(), ex);
		return new ApiErrorResponse(400, ex.getMessage());
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrorResponse constraintViolationException(ConstraintViolationException ex) {
		logger.error(ex.getMessage(), ex);
		return new ApiErrorResponse(400, "Bad Request");
	}

	@ExceptionHandler(value = { NoHandlerFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrorResponse noHandlerFoundException(NoHandlerFoundException ex) {
		logger.error(ex.getMessage(), ex);
		return new ApiErrorResponse(404, "Resource Not Found");
	}

	@ExceptionHandler(value = { HttpClientErrorException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiErrorResponse unauthorizedException(HttpClientErrorException ex) {
		logger.error(ex.getMessage(), ex);
		return new ApiErrorResponse(401, "Unauthorized");
	}

	@ExceptionHandler(value = { AccessDeniedException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiErrorResponse accessDeniedException(AccessDeniedException ex) {
		logger.error(ex.getMessage(), ex);
		return new ApiErrorResponse(401, "Unauthorized");
	}

	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiErrorResponse unknownException(Exception ex) {
		logger.error(ex.getMessage(), ex);
		return new ApiErrorResponse(500, "Internal Server Error");
	}
}
