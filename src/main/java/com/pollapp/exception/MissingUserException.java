package com.pollapp.exception;


public class MissingUserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4841312309417942478L;

	public MissingUserException(String message){
		super(message);
	}

	public MissingUserException(Exception ex) {
		super(ex);
	}
}
