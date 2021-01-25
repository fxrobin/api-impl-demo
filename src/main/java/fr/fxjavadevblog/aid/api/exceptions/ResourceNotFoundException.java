package fr.fxjavadevblog.aid.api.exceptions;

import javax.ws.rs.core.Response;

/**
 * ResourceNotFoundException is a RuntimeException which can be thrown by
 * any endpoint. This exception is wrapped and converted into JSON object
 * by a JAX-RS Exception Mapper (ApiExceptionMapper).
 * 
 * @author robin
 *
 */

@SuppressWarnings("serial")
public class ResourceNotFoundException extends ApiException {

	public ResourceNotFoundException() {
		super(Response.Status.NOT_FOUND, "Resource not found");		
	}

	
	
}
