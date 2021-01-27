package fr.fxjavadevblog.aid.utils.jaxrs.filtering;

import lombok.Getter;

public enum FilterOperation
{
	EQUALS("="), LIKE("LIKE"), GREATER_THAN(">="), LESSER_THAN("<=");
	
	@Getter
	private final String hsqlOperation;
	
	private FilterOperation(String operation) 
	{
		this.hsqlOperation = operation;
	}
}