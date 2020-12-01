package fr.fxjavadevblog.aid.health;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import fr.fxjavadevblog.aid.metadata.Application;

/**
 * Simple Health Check.
 * 
 * @author François-Xavier Robin
 *
 */

@Liveness
@ApplicationScoped
public class ApplicationHealthCheck implements HealthCheck
{
	private static StopWatch chrono = StopWatch.createStarted();
	
    @Override
    public HealthCheckResponse call()
    {
       return HealthCheckResponse.named("Application")
    		   .up()
    		   .withData("app_name", Application.APP_NAME)
    		   .withData("app_version", Application.APP_VERSION)
    		   .withData("api_version", Application.API_VERSION)
    		   .withData("started_at", DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format(chrono.getStartTime()))
    		   .withData("uptime", chrono.toString())
    		   .build();
    }
}
