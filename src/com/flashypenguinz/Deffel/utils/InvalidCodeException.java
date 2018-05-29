package com.flashypenguinz.Deffel.utils;

public class InvalidCodeException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidCodeException(String message) {
        super(message);
    }
	
	public void printException() {
		System.out.println("Error: "+super.getMessage());
	}
	
}
