package fr.fxjavadevblog.aid.metadata;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;



// this definition is merged with resources/META-INF/openapi.yml

@OpenAPIDefinition (
	info = @Info(
			title = ApplicationConfig.APP_NAME, 
			version = ApplicationConfig.APP_VERSION
			)
		
)

@ApplicationPath(ApplicationConfig.API_VERSIONED_BASE_PATH)

public class ApplicationConfig extends Application {
	
	// this constants are used by OpenApi definition and by the response of /health
	
	public static final String PATH_DELIM = "/";

	public static final String APP_NAME = "API for Atari ST Floppy Catalog";
	public static final String APP_VERSION = "0.0.2";
	public static final String API_BASE_PATH = "api";
	public static final String API_VERSION = "v1";
	public static final String API_VERSIONED_BASE_PATH = PATH_DELIM + API_BASE_PATH + PATH_DELIM + API_VERSION;

}
