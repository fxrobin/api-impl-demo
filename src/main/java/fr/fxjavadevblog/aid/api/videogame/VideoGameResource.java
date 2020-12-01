package fr.fxjavadevblog.aid.api.videogame;

import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
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

import fr.fxjavadevblog.aid.utils.UUIDProducer;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import lombok.extern.slf4j.Slf4j;

/**
 * JAX-RS endpoint for Video Games.
 * 
 * @author François-Xavier Robin
 *
 */
@Path("/api/v1/video-games")
@Tag(name="Videogames", description = "CRUD operations over videogames")
@Slf4j
@Produces({"application/json", "application/yaml"})
public class VideoGameResource
{
    
    @Inject
    VideoGameRepository videoGameRepository;
    
    @Inject 
    TransactionManager tm;

    @GET
    @Operation(summary = "Get games", 
               description = "Get all video games on Atari ST. Content negociation can produce application/json and application/yaml")
    @Timed(name = "videogames-find-all", absolute = true, description = "A measure of how long it takes to fetch all video games.", unit = MetricUnits.MILLISECONDS)
    public Response findAll(
         @Parameter(description="Page to display starting from 0", required = true)
         @QueryParam(value = "page") 
         @Min(0) @Max(Integer.MAX_VALUE) 
         int page, 
         
         @Parameter(description="Number of items to be displayed per page", required = true)
         @QueryParam(value = "size") 
         @Min(2) @Max(200)
         int size)
    {
    	log.info("findAll video-games");
    	PanacheQuery<VideoGame> query = videoGameRepository.findAll();
  
    	
    	return Response.status(Response.Status.OK)
    				   .entity(query.page(page, size).list())
    				   .build();
    }
    
    @Transactional
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response post(VideoGame videoGame) throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException
    {   
    	log.info("post video-game " + videoGame);
    	videoGame.id = UUIDProducer.produceUUIDAsString();
    	videoGameRepository.persistAndFlush(videoGame);
    	return Response.ok().entity(videoGame).build();
    }
    
    @Transactional
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") String id)
    {   	
    	log.info("delete video-game " + id);
    	videoGameRepository.deleteById(id);
    	return Response.ok().build();
    }
    
    @Transactional
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response update(VideoGame videoGame) throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException
    {   	
    	log.info("update video-game " + videoGame);
    	VideoGame vg = videoGameRepository.findById(videoGame.id);
    	vg.setName(videoGame.getName());
    	vg.setGenre(videoGame.getGenre());
    	return Response.ok().entity(vg).build();
    }
    
    

}
