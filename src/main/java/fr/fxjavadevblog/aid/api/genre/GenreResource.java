package fr.fxjavadevblog.aid.api.genre;

import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterStyle;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import fr.fxjavadevblog.aid.api.videogame.VideoGame;
import fr.fxjavadevblog.aid.api.videogame.VideoGameRepository;
import fr.fxjavadevblog.aid.utils.PagedResponse;
import fr.fxjavadevblog.aid.utils.QueryParameterUtils;
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
@Produces("application/json")
@Tag(name = "Genres", description = "Get all videogame genres or get all videogames within a genre.")
@Slf4j
public class GenreResource 
{
	@Inject
	VideoGameRepository videoGameRepository;
	

	@GET
	@Operation(summary = "Returns all genres of video games on Atari ST")
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
	public Response findByGenre(
			@PathParam("genre") 
			final Genre genre,
			
			@Parameter(description="Sort order", required = false, example = "name,-genre", allowReserved = true)
	        @QueryParam(value = "sort") 
	        final String sortingClause,
			
			@Parameter(description = "Page to display starting from 0", required = true) 
	        @QueryParam(value = "page") 
			@Min(0) 
			@Max(Integer.MAX_VALUE) 
			@DefaultValue("0")
			final int page,

			@Parameter(description = "Number of items to be displayed per page", required = true) 
	        @QueryParam(value = "size") 
	        @Min(2) 
	        @Max(200) 
			@DefaultValue("50")
			final int size) 
	{
		log.info("find video-games by genre:{} page:{} size:{}", genre, page, size);
		Sort sort = QueryParameterUtils.createSort(sortingClause);
		PanacheQuery<VideoGame> query = videoGameRepository.findByGenre(genre, sort).page(page, size);
		return  PagedResponse.of(query);
	}

}
