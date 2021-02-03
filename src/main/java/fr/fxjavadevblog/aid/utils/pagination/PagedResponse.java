package fr.fxjavadevblog.aid.utils.pagination;


import java.util.List;

import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

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
	@Schema(description = "contains the pagination metadata of the query")
	private Metadata metadata;
	
	@Getter
	@Schema(description = "contains the resource collection of the query")
	private List<T> data;
	
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
