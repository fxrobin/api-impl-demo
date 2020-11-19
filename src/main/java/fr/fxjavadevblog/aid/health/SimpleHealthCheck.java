package fr.fxjavadevblog.aid.health;

import java.time.Duration;
import java.time.Instant;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import fr.fxjavadevblog.aid.utils.DateTimeUtils;

/**
 * Simple Health Check.
 * 
 * @author François-Xavier Robin
 *
 */

@Liveness
@ApplicationScoped
public class SimpleHealthCheck implements HealthCheck
{
	private static Instant startTime = Instant.now(); 
	
    @Override
    public HealthCheckResponse call()
    {
       Duration upTime = Duration.between(startTime, Instant.now());
    	
       return HealthCheckResponse.named("Application")
    		   .up()
    		   .withData("app_name", "API for Atari ST Floppy Catalog")
    		   .withData("app_version", "1.0")
    		   .withData("api_version", "v1")
    		   .withData("started_at", startTime.toString())
    		   .withData("uptime", DateTimeUtils.format(upTime))
    		   .build();
    }
}
