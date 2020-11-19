package fr.fxjavadevblog.aid.videogame;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import fr.fxjavadevblog.aid.genre.Genre;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

/**
 * CRUD repository for the VideoGame class. Primary key is a UUID represented by a String.
 * This repository is created by Panache.
 * 
 * @author François-Xavier Robin
 *
 */

@ApplicationScoped

public class VideoGameRepository implements PanacheRepositoryBase<VideoGame, String>
{
    /**
     * gets every Video Game filtered by the given Genre.
     * 
     * @param genre
     *    genre of video game
     *    @see Genre
     *    
     * @return
     *    a list of video games
     */
    public List<VideoGame> findByGenre(Genre genre)
    {
    	return find("genre", genre).list();
    }
}