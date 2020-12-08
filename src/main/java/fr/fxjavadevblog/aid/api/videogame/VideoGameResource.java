package fr.fxjavadevblog.aid.api.videogame;

import java.lang.reflect.InvocationTargetException;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;

import fr.fxjavadevblog.aid.utils.PagedResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
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
@Produces({"application/json", "application/yaml"})

@Slf4j
public class VideoGameResource
{
    
    @Inject
    VideoGameRepository videoGameRepository;

    @GET
    @Operation(summary = "Get games", 
               description = "Get all video games on Atari ST. Content negociation can produce application/json and application/yaml")
    @Timed(name = "videogames-find-all", absolute = true, description = "A measure of how long it takes to fetch all video games.", unit = MetricUnits.MILLISECONDS)
    public Response findAll(  			
         @Parameter(description="Page to display starting from 0", required = true)
         @QueryParam(value = "page") 
         @Min(0) @Max(Integer.MAX_VALUE) 
         final int page, 
         
         @Parameter(description="Number of items to be displayed per page", required = true)
         @QueryParam(value = "size") 
         @Min(2) @Max(200)
         final int size)
    {
    	log.info("findAll video-games page:{} size:{}", page, size); 	
    	PanacheQuery<VideoGame> query = videoGameRepository.findAll().page(page, size);  	
    	return PagedResponse.of(query);
    }
  
    @Transactional
    @POST
    @Operation(summary = "Create a new game", 
    description = "Create a new game. Content negociation can produce application/json and application/yaml")
    @Consumes("application/json")
    @Produces("application/json")
    public Response post(VideoGame source) throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {   
    	log.info("post video-game {}", source);
    	VideoGame dest = CDI.current().select(VideoGame.class).get();
    	BeanUtils.copyProperties(source, dest);
    	videoGameRepository.persistAndFlush(dest);
    	return Response.ok().entity(dest).build();
    }
    
    @Transactional
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") String id)
    {   	
    	log.info("delete video-game {}", id);
    	videoGameRepository.deleteById(id);
    	return Response.ok().build();
    }
    
    @Transactional
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("{id}")
    public Response update(@PathParam("id") String id,  VideoGame source) throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {   	
    	log.info("update video-game {} : {}", id, source);
    	VideoGame dest = videoGameRepository.findById(id);
    	BeanUtils.copyProperties(source, dest);
    	return Response.ok().entity(dest).build();
    }

}
