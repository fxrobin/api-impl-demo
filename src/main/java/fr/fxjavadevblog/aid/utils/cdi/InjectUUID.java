package fr.fxjavadevblog.aid.utils.cdi;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;


/**
 * annotation to mark a field to be injected by CDI with a UUID.
 * 
 * @author François-Xavier Robin
 *
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface InjectUUID
{
}
