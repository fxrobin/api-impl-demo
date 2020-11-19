package fr.fxjavadevblog.aid.videogame;

import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import lombok.extern.slf4j.Slf4j;

/**
 * JAX-RS endpoint for Video Games.
 * 
 * @author François-Xavier Robin
 *
 */
@Path("/api/v1/video-games")
@Slf4j
@Produces({"application/json", "application/yaml"})
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
    
    

}
