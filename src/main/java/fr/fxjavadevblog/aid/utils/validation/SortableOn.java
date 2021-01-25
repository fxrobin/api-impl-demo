package fr.fxjavadevblog.aid.utils.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = SortableOnValidator.class)

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SortableOn {
	
	String[] value() default {}; 	
	
	String message() default "Sortable field validation error. One or many fields are not permitted";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
