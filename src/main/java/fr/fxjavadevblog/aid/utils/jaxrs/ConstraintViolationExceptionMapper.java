package fr.fxjavadevblog.aid.utils.jaxrs;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.Builder;
import lombok.Data;

/**
 * gestionnaire d'exception specifique pour JAX-RS.
 *
 * source : https://stackoverflow.com/questions/44308101/customizing-jax-rs-response-when-a-constraintviolationexception-is-thrown-by-bea
 * 
 */


@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException>
{
	private static final String VALIDATION_ERROR_MESSAGE = "Validation error";
	private static final String MEDIATYPE_PROBLEM_JSON = SpecificMediaType.APPLICATION_PROBLEM_JSON;

	// embedded class to wrap Validation error to be produced as JSON.
	@Data
	@Builder
	public static class ValidationError
	{
		private String message;
		private String path;
		private String details;
	}
	
    /**
     * converts the exception containing all violations into a JAX-RS response
     * with JSON.
     */
	@Override
	public Response toResponse(ConstraintViolationException exception)
	{
		List<ValidationError> errors = exception.getConstraintViolations()
				                                .stream()
				                                .map(this::toValidationError)
				                                .collect(Collectors.toList());
		return Response.status(Response.Status.PRECONDITION_FAILED)
				       .entity(errors)
				       .type(MEDIATYPE_PROBLEM_JSON)
				       .build();
	}

	/**
	 * converts a violation into a ValidationError.
	 * 
	 * @param constraintViolation
	 * @return
	 */
	private ValidationError toValidationError(ConstraintViolation<?> constraintViolation)
	{			
		return ValidationError.builder()			   
			   .message(VALIDATION_ERROR_MESSAGE)
			   .path(constraintViolation.getPropertyPath().toString())
			   .details(constraintViolation.getMessage())
			   .build();
	}
}