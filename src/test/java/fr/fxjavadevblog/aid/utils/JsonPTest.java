package fr.fxjavadevblog.aid.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.fxjavadevblog.aid.api.genre.Genre;
import fr.fxjavadevblog.aid.api.videogame.VideoGame;
import fr.fxjavadevblog.aid.api.videogame.VideoGameFactory;
import fr.fxjavadevblog.aid.global.TestingGroups;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@Tag(TestingGroups.UNIT_TESTING)
public class JsonPTest {
	
	@Test
	public void createJson() throws JsonProcessingException
	{
		VideoGame vg = VideoGameFactory.newInstance();
		ValidationUtils.consumeValidationMessages(vg, Assertions::fail);
		vg.setGenre(Genre.SHOOT_THEM_UP);
		vg.setName("Xenon Reborn");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(vg);
		System.out.println(json);
	}

}
