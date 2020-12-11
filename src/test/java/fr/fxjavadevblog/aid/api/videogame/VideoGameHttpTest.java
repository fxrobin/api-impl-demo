package fr.fxjavadevblog.aid.api.videogame;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import fr.fxjavadevblog.aid.global.TestApplicationConfig;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.specification.RequestSpecification;

/**
 * Tests for /video-games on the HTTP Layer with RestAssured
 * 
 * @author François-Xavier Robin
 *
 */

@QuarkusTest
@TestHTTPEndpoint(VideoGameResource.class) 
class VideoGameHttpTest 
{
	
	private RequestSpecification prepareTest() 
	{
		return given()
        	.baseUri(TestApplicationConfig.REST_ASSURED_FULL_BASE_URI)
        	.log().uri()
        	.when();
	}
	
    @Test
    void testHead() 
    {
        prepareTest()          
          	.head()	       
          	.then()
          		.assertThat()
          		.statusCode(Response.Status.NO_CONTENT.getStatusCode())
          		.header("Resource-Count", "1619");    
    }

    
    @Test
    void testGetOne() 
    {
        prepareTest()	          
          	.get("/098d7670-ac32-49e7-9752-93fb1d16d495")	       
          	.then()
          		.assertThat()
          		.statusCode(Response.Status.OK.getStatusCode())
          		.body(containsString("BIONIC COMMANDO"), 
          			  containsString("shoot-them-up"));          		
    }
    
    @Test
    void testGetPaginated()
    {
        prepareTest()	
        	.param("page", 0).param("size", 50)
      		.get()	       
      		.then()
	      		.assertThat()
	      		.statusCode(Response.Status.PARTIAL_CONTENT.getStatusCode())
	      		.header("Resource-Count", "1619")
	      		.body(containsString("metadata"))
	      		.body(containsString("data"))
	      		.body(containsString("resourceCount"))
                .body(containsString("resourceCount"))
                .body(containsString("pageCount"))
                .body(containsString("currentPage")); 
	      
    }
    
}
