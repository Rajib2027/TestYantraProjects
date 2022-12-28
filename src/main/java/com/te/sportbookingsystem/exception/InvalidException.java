package com.te.sportbookingsystem.exception;

public class InvalidException extends RuntimeException {

	String message;

	public InvalidException(String message) {
		super();
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
