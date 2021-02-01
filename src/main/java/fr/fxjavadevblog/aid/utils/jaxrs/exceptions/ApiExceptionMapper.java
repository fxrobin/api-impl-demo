package fr.fxjavadevblog.aid.utils.jaxrs.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import fr.fxjavadevblog.aid.api.exceptions.ApiException;
import fr.fxjavadevblog.aid.utils.jaxrs.media.SpecificMediaType;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * JAX-RS Exception Mapper Provider for any ApiException like ResourceNotFoundException.
 * 
 */

@Provider
@Slf4j
public class ApiExceptionMapper implements ExceptionMapper<ApiException>
{
	private static final String API_ERROR_MESSAGE = "API Exception";
	private static final String MEDIATYPE_PROBLEM_JSON = SpecificMediaType.APPLICATION_PROBLEM_JSON;

	// embedded class to wrap Validation error to be produced as JSON.
	@Data
	@Builder
	public static class ApiError
	{
		private String message;
		private String details;
	}
	
    /**
     * converts the exception containing all violations into a JAX-RS response
     * with JSON.
     */
	@Override
	public Response toResponse(ApiException exception)
	{
		ApiError error = toApiError(exception);	
		log.error("ApiError : {}", exception );
		return Response.status(exception.getStatus())
				       .entity(error)
				       .type(MEDIATYPE_PROBLEM_JSON)
				       .build();
	}

	/**
	 * converts a violation into a ValidationError.
	 * 
	 * @param constraintViolation
	 * @return
	 */
	private ApiError toApiError(ApiException ex)
	{			
		return ApiError.builder()			   
			   .message(API_ERROR_MESSAGE)
			   .details(ex.getMessage())
			   .build();
	}
}