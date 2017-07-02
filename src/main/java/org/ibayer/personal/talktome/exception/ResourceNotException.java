package org.ibayer.personal.talktome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Throws exception of resource not found. Http status 404 is returned.
 * @author ibrahim.bayer
 *
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with message
	 * @param message
	 */
	public ResourceNotException(String message) {
		super(message);
	}

}
