package com.nhan.exception;

public class EntityExistsException extends RuntimeException {

	private static final long serialVersionUID = -325234254631268287L;

	public EntityExistsException(String message) {
		super(message);
	}
}
