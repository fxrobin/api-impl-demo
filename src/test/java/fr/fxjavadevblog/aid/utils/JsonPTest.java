package fr.fxjavadevblog.aid.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.fxjavadevblog.aid.api.genre.Genre;
import fr.fxjavadevblog.aid.api.videogame.VideoGame;
import fr.fxjavadevblog.aid.api.videogame.VideoGameFactory;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@Tag(TestingGroups.UNIT_TESTING)
class JsonPTest {
	
	@Test
	@DisplayName("JSON serialization test")
	void createJson() throws JsonProcessingException
	{
		VideoGame vg = VideoGameFactory.newInstance();
		ValidationUtils.consumeValidationMessages(vg, Assertions::fail);
		vg.setGenre(Genre.SHOOT_THEM_UP);
		vg.setName("Xenon Reborn");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(vg);
		assertNotNull(json);
		assertTrue(json.contains("Xenon Reborn"), "JSON must contain Xenon Reborn");
		assertTrue(json.contains("shoot-them-up"), "JSON must contain shoot-them-up");
	}

}
