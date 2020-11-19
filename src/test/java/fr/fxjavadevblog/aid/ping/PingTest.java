package fr.fxjavadevblog.aid.ping;

import static io.restassured.RestAssured.given;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import fr.fxjavadevblog.aid.global.TestingGroups;
import io.quarkus.test.junit.QuarkusTest;

/**
 * Checking "ping service".
 * 
 * @see fr.fxjavadevblog.aid.ping.PingService.PingService
 * @author François-Xavier Robin
 *
 */

@QuarkusTest
@Tag(TestingGroups.UNIT_TESTING)
class PingTest
{
    public static final String ENDPOINT = "/api/v1/ping";

    @Test
    void testPing()
    {        
        given().when()
               .get(ENDPOINT)
               .then()
               .statusCode(200)
               .assertThat().body(CoreMatchers.equalTo("pong"));                                 
    }
}
