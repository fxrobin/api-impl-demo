package fr.fxjavadevblog.aid.api.videogame;

import java.util.Optional;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterStyle;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import fr.fxjavadevblog.aid.utils.PagedResponse;
import fr.fxjavadevblog.aid.utils.QueryParameterUtils;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import lombok.extern.slf4j.Slf4j;

/**
 * JAX-RS endpoint for Video Games.
 * 
 * @author François-Xavier Robin
 *
 */

// JAX-RS + OpenAPI annotations 

@Path("/video-games")
@Tag(name="Videogames", description = "CRUD operations over videogames")
@Produces({MediaType.APPLICATION_JSON, "application/yaml"})
@Slf4j
public class VideoGameResource
{    
    @Inject
    VideoGameRepository videoGameRepository;
    
    /**
     * returns META-DATA over the collections.
     * 
     * @return
     */
    @HEAD
    @Operation(summary = "Get video games metadata", description = "Get video games metadata, like the total count (Resource-Count) as HTTP RESPONSE HEADERS") 
    @APIResponse(responseCode = "204", description = "HTTP Headers contains metadata about video-game resources",
                      headers = {@Header(name = "Resource-Count", description = "total count of video games", schema = @Schema(type = SchemaType.INTEGER))})
    public Response getMetaData()
    {
    	return Response.noContent()
    			       .header("Resource-Count", videoGameRepository.count())
    			       .build();
    }

    @GET
    @Operation(summary = "Video-game resources (paging, sorting, filtering (TODO))", 
               description = "Get all video games on Atari ST. Content negociation can produce application/json and application/yaml")
    @Timed(name = "videogames-find-all", absolute = true, description = "A measure of how long it takes to fetch all video games.", unit = MetricUnits.MILLISECONDS)
    @APIResponse(responseCode = "206", description = "Partial response. Paged.")
    public Response findAll(  	
        
         @Parameter(description="Sort order", required = false, example = "name,-genre", allowReserved = true)
         @QueryParam(value = "sort") 
         final String sortingClause,
         
         @Context
         final UriInfo uriInfo,

         @Parameter(description="Page to display starting from 0", required = true)
         @QueryParam(value = "page") 
         @DefaultValue("0")
         @Min(0) @Max(Integer.MAX_VALUE) 
         final int page, 
         
         @Parameter(description="Number of items to be displayed per page", required = true)
         @QueryParam(value = "size") 
         @DefaultValue("50")
         @Min(2) @Max(200)
         final int size)
    {
        log.info("findAll video-games page:{} size:{} sort:{}", page, size, sortingClause); 	
        
        Sort sort = QueryParameterUtils.createSort(sortingClause);
        PanacheQuery<VideoGame> query = videoGameRepository.findAll(sort)
                                                           .page(page, size);  	                                                   
    	return PagedResponse.of(query);
    }
    
    @GET
    @Path("{id}")
    @Operation(summary = "Get information about a particular game.", description = "Retrieve all data of a game. *Content Negociation* can produce JSON or YAML")
    @APIResponse(responseCode = "200", description = "The game has been found.")
    @APIResponse(responseCode = "404", description = "The game is not found. The provided game ID is incorrect.")
    @Produces({MediaType.APPLICATION_JSON, "application/yaml"})
    public Response get(@PathParam("id") @NotNull String id)
    {
        log.info("get video-game {}", id);
        VideoGame vg = videoGameRepository.findById(id);
        ResponseBuilder responseBuilder =  Optional.ofNullable(vg)
                                                   .map(game -> Response.ok().entity(game))
                                                   .orElseGet(() -> Response.status(Status.NOT_FOUND));
    	return responseBuilder.build();
    }
  
    @Transactional
    @POST
    @Operation(summary = "Create a new game", description = "Create a new game.")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "The game has been created. The `Location` header contains de URI to the newly created game.")
    public Response post(VideoGame source, @Context UriInfo uriInfo) 
    {   
    	log.info("post video-game {}", source);
    	VideoGame dest = CDI.current().select(VideoGame.class).get();
        dest.setName(source.getName());
        dest.setGenre(source.getGenre());
        videoGameRepository.persistAndFlush(dest);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(dest.getId());
    	return Response.created(uriBuilder.build()).build();
    }
    
    @Transactional
    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete a game", description = "Delete the game for the given UUID.")
    @APIResponse(responseCode = "204", description = "The game has been deleted.")
    @APIResponse(responseCode = "404", description = "The game does not exist.")
    public Response delete(@PathParam("id") String id)
    {   	
    	log.info("delete video-game {}", id);
    	boolean deleted = videoGameRepository.deleteById(id);
    	return deleted ? Response.noContent().build() : Response.status(Status.NOT_FOUND).build();
    }
    
    @Transactional
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @Operation(summary = "Update a game", description = "Fully update the game for the given UUID.")
    @APIResponse(responseCode = "200", description = "The game has been modified.")
    @APIResponse(responseCode = "404", description = "The game does not exist.")
    public Response update(@PathParam("id") String id,  VideoGame source) 
    {   	
        log.info("update video-game {} : {}", id, source);
        ResponseBuilder responseBuilder;

        VideoGame dest = videoGameRepository.findById(id);
        if (dest == null)
        {
            responseBuilder = Response.status(Status.NOT_FOUND);
        }
        else
        {
            dest.setName(source.getName());
            dest.setGenre(source.getGenre());
            responseBuilder = Response.ok().entity(dest);
        }
        
        return responseBuilder.build();
    }

}
