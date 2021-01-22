package fr.fxjavadevblog.aid.utils.cdi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import fr.fxjavadevblog.aid.api.videogame.VideoGame;
import fr.fxjavadevblog.aid.api.videogame.VideoGameFactory;
import fr.fxjavadevblog.aid.utils.TestingGroups;
import fr.fxjavadevblog.aid.utils.validation.ValidationUtils;
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
    
    // Testing VideoGame @Dependent
    @Inject
    VideoGame videoGame;
    
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
    	ValidationUtils.assertValidationMessages(this, Assertions::fail);  
    }
    
    @Test
    @DisplayName("VideoGame instance injection via CDI")
    void testVideoGameInjection()
    {
        assertNotNull(videoGame);
        ValidationUtils.assertValidationMessages(videoGame, Assertions::fail);  
    }
   
    @Test
    @DisplayName("VideoGame instance via VideoGameFactory")
    void testVideoGameFactory()
    {
        VideoGame vg = VideoGameFactory.newInstance();
        assertNotNull(vg);
    }
    
}
