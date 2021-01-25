package fr.fxjavadevblog.aid.utils.jaxrs;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import fr.fxjavadevblog.aid.utils.validation.SortableOn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination 
{
	@Parameter(description="Sort order", required = false, example = "name,-genre", allowReserved = true)
    @QueryParam(value = "sort") 
    @SortableOn({"name", "genre"})
	@Getter @Setter
	private String sortingClause;
	
	@Parameter(description="Page to display starting from 0", required = true)
    @QueryParam(value = "page") 
    @DefaultValue("0")
    @Min(value=0, message="page must be equal or greater than 0") 
    @Max(value=Integer.MAX_VALUE, message="page must be equal or less than " + Integer.MAX_VALUE)
	@Getter @Setter
	private int page;
	
    @Parameter(description="Number of items to be displayed per page", required = true)
    @QueryParam(value = "size") 
    @DefaultValue("50")
    @Min(value=2, message="size must be equal or greater than 2") 
    @Max(value=200, message="size must be equal or less than 200")
	@Getter @Setter
	private int size;
}
