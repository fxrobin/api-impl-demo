package fr.fxjavadevblog.aid.api.exceptions;

import javax.ws.rs.core.Response;

import lombok.Getter;

@SuppressWarnings("serial")
public abstract class ApiException extends RuntimeException 
{
    
	@Getter
	private final Response.Status status;
	
	public ApiException(Response.Status status, String message) {
		super(message);
		this.status = status;
	}
	
	
}
