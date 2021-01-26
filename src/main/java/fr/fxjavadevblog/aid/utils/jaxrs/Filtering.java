package fr.fxjavadevblog.aid.utils.jaxrs;

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

import fr.fxjavadevblog.aid.api.exceptions.ApiException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class Filtering 
{
	@Context
	@Getter
	@Setter
	private UriInfo uriInfo;
	
	@Getter
	private Class<?> modelClass;
	
	@Getter
	private List <Filter> filters;
	
	private static Set <String> reservedWords = Stream.of("page","size","sort")
			                                          .collect(Collectors.toSet());
	
	public enum Operation
	{
		EQUALS("="), LIKE("LIKE"), GREATER_THAN(">="), LESSER_THAN("<="), BETWEEN("TODO");
		
		@Getter
		private final String hsqlOperation;
		
		private Operation(String operation) {
			this.hsqlOperation = operation;
		}
	}
	
	public static Map<String, Operation> operationAliases = new HashMap<>();
	
	public static Pattern pattern = Pattern.compile("^(like|eq|gte|lte){1}(:)(.*)");
	
	static
	{
		operationAliases.put("equ", Operation.EQUALS);
		operationAliases.put("like", Operation.LIKE);
		operationAliases.put("gte",  Operation.GREATER_THAN);
		operationAliases.put("gte",  Operation.GREATER_THAN);		
		operationAliases.put("between",  Operation.BETWEEN);
	}	
	
	@Builder
	@Getter
	public static class Filter
	{
		private String field;
		private Operation operation;
		private Class<?> type;
		private Object value;
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(field);
			builder.append(" ");
			builder.append(operation.getHsqlOperation());
			builder.append(" ");
			builder.append(":");			
			builder.append(field);	
			return builder.toString();
		}
	}
	
	public void setModelClass(Class <?> clazz)
	{
		this.modelClass = clazz;
		filters = new LinkedList<Filtering.Filter>();	
		MultivaluedMap <String, String> parameters = uriInfo.getQueryParameters();	
		parameters.forEach(convertParameterToFilter());		
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
					Class <?> targetType = modelClass.getDeclaredField(k).getType();
					
					// inits.
					Operation operation = Operation.EQUALS;
					String value = v.get(0);
					
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
					
					Filter f = Filter.builder()
					      .field(k)
					      .operation(operation)
					      .value(convertedValue)
					      .type(targetType)
					      .build();
					log.info("Adding filter [{}] {} [{}]", f.field, f.operation, f.value);
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


}
