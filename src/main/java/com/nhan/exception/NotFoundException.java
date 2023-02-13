package com.nhan.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4252845616181526300L;

	public NotFoundException(String message) {
		super(message);
	}
}
