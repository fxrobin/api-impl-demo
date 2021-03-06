package fr.fxjavadevblog.aid.api.videogame;

import javax.enterprise.context.ApplicationScoped;

import fr.fxjavadevblog.aid.api.genre.Genre;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

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
    public PanacheQuery<VideoGame> findByGenre(Genre genre, Sort sort)
    {
    	return this.find("genre", sort, genre);
    }
}