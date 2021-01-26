package fr.fxjavadevblog.aid.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.fxjavadevblog.aid.api.genre.Genre;
import fr.fxjavadevblog.aid.api.videogame.VideoGame;
import fr.fxjavadevblog.aid.api.videogame.VideoGameFactory;
import fr.fxjavadevblog.aid.utils.validation.ValidationUtils;
import io.quarkus.test.junit.QuarkusTest;

@Tag(TestingGroups.UNIT_TESTING)
class PropertiesConvertionTest {
	
	@Test
	@DisplayName("Map<String, String> to VideoGame")
	void createJson() throws JsonProcessingException
	{
		VideoGame vg = VideoGameFactory.newInstance();
		
		
	}

}
