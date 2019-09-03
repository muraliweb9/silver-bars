package com.murali.me.service;

/**
 * 
 * Custom exception to represent an invalid order placed or cancelled
 * 
 * @author murali
 *
 */
public class InvalidOrderException extends Exception {

	private static final long serialVersionUID = -2600102015205157601L;
	
	public InvalidOrderException(String message) {
        super(message);
    }

}
