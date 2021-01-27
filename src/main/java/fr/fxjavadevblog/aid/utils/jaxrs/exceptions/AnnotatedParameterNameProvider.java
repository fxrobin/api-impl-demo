package fr.fxjavadevblog.aid.utils.jaxrs.exceptions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ParameterNameProvider;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

/**
 * This provider finds parameters names through annotation.
 * 
 * 
 * 
 * @author robin
 *
 */

public class AnnotatedParameterNameProvider implements ParameterNameProvider {
	  @Override
	  public List<String> getParameterNames(Constructor<?> constructor) {
	    return lookupParameterNames(constructor.getParameterAnnotations());
	  }

	  @Override
	  public List<String> getParameterNames(Method method) {
	    return lookupParameterNames(method.getParameterAnnotations());
	  }

	  private List<String> lookupParameterNames(Annotation[][] annotations) {
	    final List<String> names = new ArrayList<>();
	    if (annotations != null) {
	      for (Annotation[] annotation : annotations) {
	        String annotationValue = null;
	        for (Annotation ann : annotation) {
	          annotationValue = getAnnotationValue(ann);
	          if (annotationValue != null) {
	            break;
	          }
	        }

	        // if no matching annotation, must be the request body
	        if (annotationValue == null) {
	          annotationValue = "requestBody";
	        }
	        names.add(annotationValue);
	      }
	    }

	    return names;
	  }

	  private static String getAnnotationValue(Annotation annotation) {
	    if (annotation instanceof HeaderParam) {
	      return ((HeaderParam) annotation).value();
	    } else if (annotation instanceof PathParam) {
	      return ((PathParam) annotation).value();
	    } else if (annotation instanceof QueryParam) {
	      return ((QueryParam) annotation).value();
	    }
	    return null;
	  }
	}