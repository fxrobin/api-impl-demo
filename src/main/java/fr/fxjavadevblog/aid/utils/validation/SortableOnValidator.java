package fr.fxjavadevblog.aid.utils.validation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import fr.fxjavadevblog.aid.utils.commons.Splitter;

/**
 * Implementation of SortableOn() annotation.
 * 
 * @author robin
 *
 */
public class SortableOnValidator implements ConstraintValidator<SortableOn, String>  {
	
	 private Set <String> validFields;
	
	@Override
	public void initialize(SortableOn constraintAnnotation) 
	{
		validFields = new HashSet<>(Arrays.asList(constraintAnnotation.value()));
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) 
	{				
		return StringUtils.isAllEmpty(value) || checkDeclaredFields(value, context) ;	
	}

	private boolean checkDeclaredFields(String value, ConstraintValidatorContext context) 
	{
		String cleanedValue=StringUtils.remove(value, '-'); // removing any "-" for DESC sort.
		List <String> fields = Splitter.split(cleanedValue);
		boolean valid = true;
		for(String field : fields)
		{
			if (!validFields.contains(field))
			{
				context.disableDefaultConstraintViolation(); 
				context.buildConstraintViolationWithTemplate("Sorting on " + field + " is not allowed.")
			           .addConstraintViolation();
				valid = false;
			}
		}
		return valid;
	}

}
