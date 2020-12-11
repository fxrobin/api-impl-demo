package fr.fxjavadevblog.aid.utils;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * Utility class to deal with Bean Validation and Objects.
 * 
 * @author robin
 *
 */
public final class ValidationUtils 
{	
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	private ValidationUtils() {
		// protection
	}
	
	/**
	 * validates an object and serialize validation errors into a string.
	 * 
	 * @param o
	 * 		the object to be validated.
	 * @return
	 * 		a string containing all validation errors or null if no errors were detected by Bean Validation.
	 */
	public static String getAllValidationMessages(Object o)
	{
		Set <ConstraintViolation <Object>> constraints = validator.validate(o);
	    return constraints.isEmpty() ? null : constraints.stream()
				  .map( v -> String.format("%s : %s", v.getPropertyPath(), 
						  							  v.getMessage()))
				  .collect(Collectors.joining("|"));	    
	}
	
	/**
	 * validates an object and serialize validation errors into a string which is sent to a Consumer<String>.
	 * 
	 * @param o
	 * 		the object to be validated.
	 * @param consumer
	 * 		the consumer which will be called if there is any validation error.
	 */
	public static void assertValidationMessages(Object o, Consumer <String> consumer)
	{
		String message = getAllValidationMessages(o);
		if (message != null) consumer.accept(message);
	}
	
}
