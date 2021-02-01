package fr.fxjavadevblog.aid.api.videogame;

import java.net.URI;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import fr.fxjavadevblog.aid.api.exceptions.ResourceNotFoundException;
import fr.fxjavadevblog.aid.api.genre.Genre;
import fr.fxjavadevblog.aid.metadata.VideoGamePagedResponse;
import fr.fxjavadevblog.aid.utils.jaxrs.fields.FieldSet;
import fr.fxjavadevblog.aid.utils.jaxrs.filtering.Filtering;
import fr.fxjavadevblog.aid.utils.jaxrs.media.SpecificMediaType;
import fr.fxjavadevblog.aid.utils.jaxrs.pagination.Pagination;
import fr.fxjavadevblog.aid.utils.jaxrs.pagination.QueryParameterUtils;
import fr.fxjavadevblog.aid.utils.pagination.PagedResponse;
import fr.fxjavadevblog.aid.utils.validation.SortableOn;
import fr.fxjavadevblog.preconditions.Checker;
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
@Tag(name="Videogames", description = "Videogame collection")
@Produces({MediaType.APPLICATION_JSON, SpecificMediaType.APPLICATION_YAML})
@Slf4j
public class VideoGameResource
{    
    private static final String VIDEOGAME_ARG = "videogame";
	@Inject
    VideoGameRepository videoGameRepository;
    
    /**
     * returns META-DATA over the collections.
     * 
     * @return
     */
    @HEAD
    @Operation(summary = "Get video game metadata", description = "Get video games metadata, like the total count (Resource-Count) as HTTP RESPONSE HEADERS") 
    @APIResponse(responseCode = "204", description = "HTTP Headers contains metadata about video game resources",
                      headers = {@Header(name = "Resource-Count", description = "total count of video games", schema = @Schema(type = SchemaType.INTEGER))})
    public Response getMetaData()
    {
    	return Response.noContent()
    			       .header("Resource-Count", videoGameRepository.count())
    			       .build();
    }

    @GET
    @Operation(summary = "Video game resources with paging, sorting and filtering.)", 
               description = "Get all video games on Atari ST. Content negociation can produce application/json and application/yaml")
    @Timed(name = "videogames-find-all", absolute = true, description = "A measure of how long it takes to fetch all video games.", unit = MetricUnits.MILLISECONDS)
    @Parameter(in = ParameterIn.QUERY, name="name", description = "holds filtering expression : like, eq" )
    @Parameter(in = ParameterIn.QUERY, name="genre", description = "holds filtering expression : eq")
    @APIResponse(responseCode = "206", description = "Partial response. Paged.", content= {@Content( schema=@Schema(implementation = VideoGamePagedResponse.class))})
    @APIResponse(responseCode = "412", description = "Invalid parameters.", content= { @Content(mediaType=SpecificMediaType.APPLICATION_PROBLEM_JSON) } )
    public Response findAll(@BeanParam 
    		                @Valid 
    		                final Pagination pagination, 
    		                
    		                @Parameter(description="Sort order", required = false, allowReserved = true, example="name,genre")
                            @QueryParam(value = "sort") 
                            @SortableOn({"name","genre"})
	                        final String sortingClause,
	                        
	    		            @Parameter(description = "comma separated values of the returned fields",  allowReserved = true, example = "name,genre")
	    		            @QueryParam(value = "fields")
	                        String fields,
    		                
	                        @Context
	                        final UriInfo uriInfo)
    {
        log.info("findAll video games. Pagination : {}", pagination); 	
        
        PanacheQuery<VideoGame> query;
        Sort sort = QueryParameterUtils.createSort(sortingClause);
        
        Filtering filtering = Filtering.of(VideoGame.class, uriInfo);            
        
        if (!filtering.isFilterPresent())
        {
        	query = videoGameRepository.findAll(sort);
        }
        else
        {
        	String hqlQueryString = filtering.getQuery();
        	log.info("query string : {}", hqlQueryString);
        	query = videoGameRepository.find(hqlQueryString, sort, filtering.getParameterMap());        	
        }        
      
        query = query.page(pagination.getPage(), pagination.getSize());       
    	return PagedResponse.builder().query(query).fieldSetExpression(fields).build().getResponse();
    }
    
    @GET
    @Path("{id}")
    @Operation(summary = "Get information about a particular video game.", description = "Retrieve all data of a video game. *Content Negociation* can produce JSON or YAML")
    @APIResponse(responseCode = "200", description = "The video game has been found.", content = @Content(schema = @Schema(implementation = VideoGame.class)))
    @APIResponse(responseCode = "404", description = "The video game is not found. The provided game ID is incorrect.", content= { @Content(mediaType=SpecificMediaType.APPLICATION_PROBLEM_JSON) })
    public Response get(@PathParam(value = "id")
                        @Parameter(description = "id of the videogame", example = "098d7670-ac32-49e7-9752-93fb1d16d495")
                        @NotNull 
                        String id, 
                        
    		            @Parameter(description = "comma separated values of the returned fields", allowReserved = true, example = "name,genre")
    		            @QueryParam(value = "fields")
                        String fields)
    {
        log.info("get video-game {}", id);
        VideoGame vg = videoGameRepository.findById(id);
        Checker.notNull(VIDEOGAME_ARG, vg, ResourceNotFoundException::new);    
    	return Response.ok(FieldSet.getJson(fields, vg)).build();
    }
  
    @Transactional
    @POST
    @Operation(summary = "Create a new game", description = "Create a new videogame.")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "The videogame has been created. The `Location` header contains the URI to the newly created game."
                 , headers = @Header(name = "Location", schema = @Schema(type = SchemaType.STRING))
    )
    public Response post(VideoGame source, @Context UriInfo uriInfo) 
    {   
    	log.info("post videogame {}", source);
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
    @Operation(summary = "Delete a videogame", description = "Delete the videogame for the given UUID.")
    @APIResponse(responseCode = "204", description = "The videogame has been deleted.")
    @APIResponse(responseCode = "404", description = "The videogame does not exist.", content= { @Content(mediaType=SpecificMediaType.APPLICATION_PROBLEM_JSON) })
    public Response delete(@PathParam("id") String id)
    {   	
    	log.info("Delete videogame {}", id);
    	boolean deleted = videoGameRepository.deleteById(id);
    	return deleted ? Response.noContent().build() : Response.status(Status.NOT_FOUND).build();
    }
    
    @Transactional
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @Operation(summary = "Update a videogame", description = "Fully update the videogame for the given UUID.")
    @APIResponse(responseCode = "200", description = "The videogame has been modified.")
    @APIResponse(responseCode = "404", description = "The videogame does not exist.", content= { @Content(mediaType=SpecificMediaType.APPLICATION_PROBLEM_JSON) })
    public VideoGame update(@PathParam("id") String id,  VideoGame source) 
    {   	
        log.info("update video-game {} : {}", id, source);

        VideoGame dest = videoGameRepository.findById(id);
        Checker.notNull(VIDEOGAME_ARG, dest, ResourceNotFoundException::new);
        
        dest.setName(source.getName());
        dest.setGenre(source.getGenre());
        
        return dest;
    }
    
    @Transactional
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("{id}/name")
    @Operation(summary = "Update the name of a videogame", description = "Only update the name of the videogame for the given UUID.")
    @APIResponse(responseCode = "204", description = "The videogame has been modified.", headers = @Header(name = "Location", description = "URI of the updated video game."))
    @APIResponse(responseCode = "404", description = "The videogame does not exist.", content= { @Content(mediaType=SpecificMediaType.APPLICATION_PROBLEM_JSON) })
    public Response updateName(@PathParam("id") String id, String name, @Context UriInfo uriInfo) 
    {   	
        log.info("update video-game {} : name={}", id, name);

        VideoGame dest = videoGameRepository.findById(id);
        Checker.notNull(VIDEOGAME_ARG, dest, ResourceNotFoundException::new);        
        
        dest.setName(name);                
        
        URI uri = uriInfo.getBaseUriBuilder().path(VideoGameResource.class).path("{id}").build(id);                      
        return Response.seeOther(uri).build();
    }
    
    @Transactional
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}/genre")
    @Operation(summary = "Update the genre of a videogame", description = "Only update the genre of the videogame for the given UUID.")
    @APIResponse(responseCode = "204", description = "The videogame has been modified.", headers = @Header(name = "Location", description = "URI of the updated video game."))
    @APIResponse(responseCode = "404", description = "The videogame does not exist.", content= { @Content(mediaType=SpecificMediaType.APPLICATION_PROBLEM_JSON) })
    public Response updateGenre(@PathParam("id") String id, Genre genre, @Context UriInfo uriInfo) 
    {   	
        log.info("update video-game {} : genre={}", id, genre);

        VideoGame dest = videoGameRepository.findById(id);
        Checker.notNull(VIDEOGAME_ARG, dest, ResourceNotFoundException::new);        
        
        dest.setGenre(genre);               
        
        URI uri = uriInfo.getBaseUriBuilder().path(VideoGameResource.class).path("{id}").build(id);                      
        return Response.noContent().header("Location", uri.toString()).build();
    }

}
