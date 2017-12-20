package com.scholarum.common.exception;

@SuppressWarnings("serial")
public class ScException extends RuntimeException {

	private final int status;
	private final String message;

	public ScException(int status, String message) {
		super(message);
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
