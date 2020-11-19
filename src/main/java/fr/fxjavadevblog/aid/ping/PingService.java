package fr.fxjavadevblog.aid.ping;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.openapi.annotations.Operation;

/**
 * Simple JAX-RS endoint to check if the application is running.
 * 
 * @author François-Xavier Robin
 *
 */

@Path("/api/v1/ping")
public class PingService
{
    @GET
    @Operation(summary = "Get pong", description = "returns a simple reponse as ping/pong ")
    @Produces("text/plain")
    public String ping()
    {
        return "pong";
    }
}
