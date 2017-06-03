package com.pollapp.exception;

public class MissingPollException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6058055937048178438L;

	public MissingPollException(String message) {
		super(message);
	}
	
	public MissingPollException(Exception ex){
		super(ex);
	}
}
