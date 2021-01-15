package fr.fxjavadevblog.aid.health;

import static org.apache.commons.lang3.time.DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import fr.fxjavadevblog.aid.metadata.ApplicationConfig;

/**
 * Simple Health Check.
 * 
 * @author François-Xavier Robin
 *
 */


@ApplicationScoped
public class ApplicationHealthCheck 
{
	private static StopWatch chrono = StopWatch.createStarted();
	
	@Liveness
    public HealthCheck firstApiCheck()
    {
       return () -> HealthCheckResponse.named(ApplicationConfig.APP_NAME + " health check")
    		   .up()
    		   .withData("app_name", ApplicationConfig.APP_NAME)
    		   .withData("app_version", ApplicationConfig.APP_VERSION)
    		   .withData("api_version", ApplicationConfig.API_VERSION)
    		   .withData("api_base_uri", ApplicationConfig.API_VERSIONED_BASE_PATH)
    		   .withData("started_at", ISO_8601_EXTENDED_DATETIME_FORMAT.format(chrono.getStartTime()))
    		   .withData("uptime", chrono.toString())
    		   .build();
    }
	
}
