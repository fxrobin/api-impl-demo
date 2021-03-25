package fr.fxjavadevblog.aid.utils.openapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.PathItem;
import org.eclipse.microprofile.openapi.models.Paths;

import fr.fxjavadevblog.aid.metadata.ApplicationConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * OpenAPI filter to remove the basePath from every JAX-RS Paths.
 * 
 * @author robin
 *
 */

@Slf4j
public class OpenApiFilter implements OASFilter {
	
@Override
public void filterOpenAPI(OpenAPI openAPI) {
    
	Paths paths = openAPI.getPaths();
	
	Map <String, PathItem> map = paths.getPathItems();
	
	Map <String, PathItem> newMap = new HashMap<>();
	
	// let's remove the base path from every path.
	
	for(Entry <String, PathItem> entry : map.entrySet())
	{
		String jaxRsPath = entry.getKey();
		String openApiPath = StringUtils.remove(jaxRsPath,ApplicationConfig.API_FULL_PATH);
	    log.info("JAX-RS Path : {} converted to OpenAPI Path : {}", jaxRsPath, openApiPath);	
	    newMap.put(openApiPath, entry.getValue());
	}
	
	// new map is contructed, let's replace the definition with it.
	paths.setPathItems(newMap);
}

}
