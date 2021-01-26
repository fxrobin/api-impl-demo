package fr.fxjavadevblog.aid.utils.jaxrs;


/**
 * Specific MediaType which are not defined in JAX-RS.
 * 
 * @author robin
 *
 */
public final class SpecificMediaType 
{
	public static final String APPLICATION_YAML = "application/yaml";
	public static final String APPLICATION_PROBLEM_JSON = "application/problem+json";
		
	private SpecificMediaType() 
	{
		// protection
	}

}
