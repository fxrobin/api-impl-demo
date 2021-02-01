package fr.fxjavadevblog.aid.utils.pagination;


import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.fxjavadevblog.aid.utils.jaxrs.fields.FieldSet;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PagedResponse <T> 
{
	@Getter
	private PagedMetadata metadata;
	
	@Getter
	private T data;
	
	@JsonIgnore
	private PanacheQuery<?> query;
	
	@JsonIgnore
	private String fieldSetExpression;
	
	@JsonIgnore
	public Response getResponse()
	{
	  PagedResponse<?> pagedResponse =	PagedQueryWrapper.wrap(query);
	  String result = FieldSet.getJson(fieldSetExpression, pagedResponse);
	  return Response.status(Response.Status.PARTIAL_CONTENT)
	   .header("Resource-Count", pagedResponse.getMetadata().getResourceCount())
	   .entity(result)
	   .build();
	}

}
