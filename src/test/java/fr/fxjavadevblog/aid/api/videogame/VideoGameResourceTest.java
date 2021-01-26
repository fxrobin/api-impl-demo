package fr.fxjavadevblog.aid.api.videogame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.fxjavadevblog.aid.api.genre.Genre;
import fr.fxjavadevblog.aid.utils.jaxrs.Pagination;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class VideoGameResourceTest extends VideoGameResource {

	@BeforeEach
	void checkInjection() {
		assertNotNull(this.videoGameRepository);
	}

	@Test
	void testFindAll() {
		Response response = this.findAll(Pagination.builder().page(0).size(50).build(), null, null);
		assertCorrectResponse(response);
	}

	// BIONIC COMMANDO : 098d7670-ac32-49e7-9752-93fb1d16d495
	@Test
	void testGet() {
		String id = "098d7670-ac32-49e7-9752-93fb1d16d495";
		VideoGame vg = this.get(id);
		assertNotNull(vg);
		assertEquals("BIONIC COMMANDO", vg.getName());
		assertEquals(Genre.SHOOT_THEM_UP, vg.getGenre());
		assertEquals(0L, vg.getVersion());
		assertEquals(id, vg.getId());
	}

	@Test
	void testGetMetaData() {
		long count = this.videoGameRepository.count();
		assertTrue(count > 0);
		Response response = this.getMetaData();
		assertStatusCodeCanBe(response, Response.Status.NO_CONTENT);
		assertEquals(count, Long.parseLong(response.getHeaderString("Resource-Count")));
	}

	private void assertCorrectResponse(Response response) {
		assertNotNull(response);
		assertStatusCodeCanBe(response, Response.Status.OK, Response.Status.PARTIAL_CONTENT);
	}

	private static boolean assertStatusCodeCanBe(Response response, Response.Status... statusCode) {
		return Stream.of(statusCode)
				     .mapToInt(Response.Status::getStatusCode)
				     .anyMatch(code -> code == response.getStatus());
	}

}
