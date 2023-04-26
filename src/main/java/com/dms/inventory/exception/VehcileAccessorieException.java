package com.dms.inventory.exception;

import org.springframework.http.HttpStatus;

public class VehcileAccessorieException extends RuntimeException {

	private HttpStatus status;

	public VehcileAccessorieException() {
		super();
	}

	public VehcileAccessorieException(String s) {
		super(s);
	}

	public VehcileAccessorieException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public VehcileAccessorieException(String s, HttpStatus status) {
		super(s);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
