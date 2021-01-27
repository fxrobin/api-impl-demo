package fr.fxjavadevblog.aid.utils.jaxrs.filtering;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Filter
{
	private String field;
	private FilterOperation operation;
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