package fr.fxjavadevblog.aid.metadata;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;



// this definition is merged with resources/META-INF/openapi.yml

@OpenAPIDefinition (
		info = @Info(
					title = Application.APP_NAME, 
					version = Application.APP_VERSION
				)
		
)

public class Application extends javax.ws.rs.core.Application {
	
	// this constants are used by OpenApi definition and by the response of /health

	public static final String APP_NAME = "API for Atari ST Floppy Catalog";
	public static final String APP_VERSION = "1.0.0";
	public static final String API_VERSION = "v1";

}
