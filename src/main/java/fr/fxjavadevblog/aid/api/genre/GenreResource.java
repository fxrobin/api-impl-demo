package fr.fxjavadevblog.aid.api.genre;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import fr.fxjavadevblog.aid.api.videogame.VideoGame;
import fr.fxjavadevblog.aid.api.videogame.VideoGameRepository;
import fr.fxjavadevblog.aid.utils.jaxrs.pagination.Pagination;
import fr.fxjavadevblog.aid.utils.jaxrs.pagination.QueryParameterUtils;
import fr.fxjavadevblog.aid.utils.pagination.PagedResponse;
import fr.fxjavadevblog.aid.utils.validation.SortableOn;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import lombok.extern.slf4j.Slf4j;

/**
 * JAX-RS endpoint for genre of video games.
 * 
 * @author François-Xavier Robin
 *
 */

@Path("/genres")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Genres", description = "Get all videogame genres or get all videogames within a genre.")
@Slf4j
public class GenreResource 
{
	@Inject
	VideoGameRepository videoGameRepository;
	

	@GET
	@Operation(summary = "Returns all genres of video games on Atari ST")
	@APIResponse(responseCode = "200", description = "OK")
	public Genre[] getAllGenres() {
		return Genre.values();
	}

	@GET
	@Operation(summary = "Get games within a genre", description = "Get all video games of the given genre")
	@Path("/{genre}/video-games")
	@Timed(name = "videogames-find-by-genre", 
		   absolute = true, 
		   description = "A measure of how long it takes to fetch all video games filtered by a given genre.", 
		   unit = MetricUnits.MILLISECONDS)
	@APIResponse(responseCode = "206", description = "Partial response. Paged.")
	public Response findByGenre(@PathParam("genre") 
	                            final Genre genre,
	                            
	                            @BeanParam 
	    		                @Valid 
	    		                final Pagination pagination, 
	                            
			                    @Parameter(description="Sort order", required = false, allowReserved = true)
                                @QueryParam(value = "sort") 
                                @SortableOn({"name","genre"})
                                final String sortingClause,
    
                                @Context
                                final UriInfo uriInfo) 
	{
		log.info("find video-games by genre. Pagination : ", pagination);
		Sort sort = QueryParameterUtils.createSort(sortingClause);
		PanacheQuery<VideoGame> query = videoGameRepository.findByGenre(genre, sort)
				                                           .page(pagination.getPage(), pagination.getSize());
		return  PagedResponse.of(query);
	}

}
