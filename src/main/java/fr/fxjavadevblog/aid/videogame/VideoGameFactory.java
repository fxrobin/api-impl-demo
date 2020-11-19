package fr.fxjavadevblog.aid.videogame;


import javax.enterprise.inject.spi.CDI;

/**
 * Factory for Video Games.
 * 
 * @author François-Xavier Robin
 *
 */

public class VideoGameFactory
{
    private VideoGameFactory()
    {
        // protection for outer calls.
    }
    
    /**
     * creates and brand new VideoGame instance with its own UUID as PK.
     *
     * @return instance of a VideoGame
     */
    public static VideoGame newInstance()
    {
        // ask CDI for the instance, injecting required dependencies.
        return CDI.current().select(VideoGame.class).get();
    }
}
