package fr.fxjavadevblog.aid.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import fr.fxjavadevblog.aid.utils.InjectUUID;
import fr.fxjavadevblog.aid.videogame.VideoGame;
import fr.fxjavadevblog.aid.videogame.VideoGameFactory;
import fr.fxjavadevblog.aid.global.TestingGroups;
import io.quarkus.test.junit.QuarkusTest;

/**
 * CDI injections tests.
 * 
 * @author François-Xavier Robin
 *
 */

@QuarkusTest
@Tag(TestingGroups.UNIT_TESTING)
class CDITest
{
    private static final String UUID_PATTERN = "([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})";
    
    // Testing @InjectedUUID
    @Inject
    @InjectUUID
    @NotNull
    @Pattern(regexp = UUID_PATTERN)
    String uuid;

    /**
     * test de l'injection de UUID
     */
    @Test
    @DisplayName("UUID Injection")
    void testUUID()
    {
    	ValidationUtils.consumeValidationMessages(this, Assertions::fail);  
    }
    
    // Testing VideoGame @Dependent
    @Inject
    VideoGame videoGame;
    
    @Test
    @DisplayName("VideoGame instance injection via CDI")
    void testVideoGameInjection()
    {
        assertNotNull(videoGame);
        ValidationUtils.consumeValidationMessages(videoGame, Assertions::fail);  
    }
   
    @Test
    @DisplayName("VideoGame instance via VideoGameFactory")
    void testVideoGameFactory()
    {
        VideoGame vg = VideoGameFactory.newInstance();
        assertNotNull(vg);
    }
    
}
