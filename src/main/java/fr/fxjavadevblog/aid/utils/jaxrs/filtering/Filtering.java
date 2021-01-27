package fr.fxjavadevblog.aid.utils.jaxrs.filtering;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections4.CollectionUtils;

import fr.fxjavadevblog.aid.api.exceptions.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Filtering 
{
	@Context
	@Getter
	@Setter
	private UriInfo uriInfo;
	
	@Getter
	private Class<?> modelClass;
	
	private List <Filter> filters;
	
	private static Set <String> reservedWords = Stream.of("page","size","sort")
			                                          .collect(Collectors.toSet());
	
	public enum Operation
	{
		EQUALS("="), LIKE("LIKE"), GREATER_THAN(">="), LESSER_THAN("<=");
		
		@Getter
		private final String hsqlOperation;
		
		private Operation(String operation) 
		{
			this.hsqlOperation = operation;
		}
	}
	
	private static final Map<String, Operation> operationAliases = new HashMap<>();
	
	private static final Pattern pattern = Pattern.compile("^(like|eq|gte|lte){1}(:)(.*)");
	
	static
	{
		operationAliases.put("equ", Operation.EQUALS);
		operationAliases.put("like", Operation.LIKE);
		operationAliases.put("gte",  Operation.GREATER_THAN);
		operationAliases.put("gte",  Operation.GREATER_THAN);		
	}	
	
	public static Filtering of(Class <?> clazz, UriInfo uriInfo)
	{
		Filtering f = new Filtering();
		f.modelClass = clazz;
		f.filters = new LinkedList<>();	
		MultivaluedMap <String, String> parameters;
		if (uriInfo != null)
	    {
			 parameters = uriInfo.getQueryParameters();
			 parameters.forEach(f.convertParameterToFilter());
	    }
		else
		{
			log.info("No uriInfo to get parameters from");
		}	
		return f;
	}
	
	public boolean isFilterPresent()
	{
		return CollectionUtils.isNotEmpty(filters);
	}
	
	
	public String getQuery()
	{
		StringJoiner joiner = new StringJoiner(" AND ");
		filters.stream().map(Filter::toString).forEach(joiner::add);
		return joiner.toString();	
	}
	
	public Map<String, Object> getParameterMap()
	{
		Map <String, Object> parameters = new HashMap<>();
		filters.forEach(f -> parameters.put(f.getField(), f.getValue()));				
		return parameters;
	}
	

	private BiConsumer<? super String, ? super List<String>> convertParameterToFilter() 
	{
		return (k, v) -> {
			if (!reservedWords.contains(k))
			{
				try
				{
					Filter f = createFilter(k, v.get(0));
					log.info("Adding filter [{}] {} [{}]", f.getField(), f.getOperation(), f.getValue());
					filters.add(f);
				}
				catch(NoSuchFieldException ex)
				{
					log.error(ex.toString());
					throw new ApiException(Status.BAD_REQUEST, "No such field: " + ex.getMessage());
				}
			}
		};
	}


	
	private Filter createFilter(String paramName, String paramValue) throws NoSuchFieldException 
	{
		Class <?> targetType = modelClass.getDeclaredField(paramName).getType();
		
		// inits.
		Operation operation = Operation.EQUALS;
		String value = paramValue;
		
		// testing if the value as a specific operation "like:", "gte:", etc.
		Matcher matcher = pattern.matcher(value);
		if (matcher.matches())
		{				
			String operationValue = matcher.group(1);
			operation = operationAliases.get(operationValue);
		    value = matcher.group(3);
		    if (operation == Operation.LIKE)
		    {
		    	value = "%"+value+"%";
		    }
		}
		
		Object convertedValue = ConvertUtils.convert(value, targetType);
		
		return Filter.builder()
		      .field(paramName)
		      .operation(operation)
		      .value(convertedValue)
		      .type(targetType)
		      .build();
	}


}
