package fr.fxjavadevblog.aid.utils.jaxrs.fields;

import java.util.Arrays;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import fr.fxjavadevblog.aid.api.exceptions.ApiException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldSet {
	
	public static final String ID = "FieldSet";
	
	private FieldSet() {
		// protection
	}
	
	public static String getJson(String fieldSetExpression, Object value)
	{	
		ObjectMapper om = createObjectMapper(fieldSetExpression);
		
		try {
			return om.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			log.error("JSON conversion error : {}", e.getMessage());
			throw new ApiException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	public static ObjectMapper createObjectMapper(String fieldSetExpression) {
		ObjectMapper om = new ObjectMapper();
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		
		if (StringUtils.isNotEmpty(fieldSetExpression))
		{
			String[] fields =  StringUtils.split(fieldSetExpression, ',');
			fields = StringUtils.stripAll(fields);
		    filterProvider.addFilter(ID, SimpleBeanPropertyFilter.filterOutAllExcept(fields));	   
		    log.info("Field filters: {}", Arrays.asList(fields));
		}
		else
		{
			filterProvider.addFilter(ID, SimpleBeanPropertyFilter.serializeAll());	 
		}
		
		om.setFilterProvider(filterProvider);
		return om;
	}
	
	public static ObjectMapper createObjectMapper()
	{
		return createObjectMapper(null);
	}

}
