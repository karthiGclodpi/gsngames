package com.gsngame.exception;

public class GSNException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 812534100873979224L;

	public GSNException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	private String errorMessage;

}
