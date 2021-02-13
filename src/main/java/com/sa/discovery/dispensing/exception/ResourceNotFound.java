package com.sa.discovery.dispensing.exception;

/**
 * 
 * @author dineshmetkari
 *
 */
public class ResourceNotFound extends Exception{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFound() {
        super();
    }

    public ResourceNotFound(String message) {
        super(message);
    }

    public ResourceNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
