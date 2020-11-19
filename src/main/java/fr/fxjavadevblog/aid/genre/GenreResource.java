package fr.fxjavadevblog.aid.genre;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;

import fr.fxjavadevblog.aid.videogame.VideoGame;
import fr.fxjavadevblog.aid.videogame.VideoGameRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * JAX-RS endpoint for genre of video games.
 * 
 * @author François-Xavier Robin
 *
 */

@Path("/api/v1/genres")
@Produces("application/json")
@Slf4j
public class GenreResource {

	@Inject
    GenreResource(VideoGameRepository videoGameRepository) {
       this.videoGameRepository = videoGameRepository;
    }

	private VideoGameRepository videoGameRepository;

	@GET
	@Operation(summary = "returns all genres of video games on Atari ST")
	public Genre[] getAllGenres() {
		return Genre.values();
	}

	@GET
	@Operation(summary = "Get games within a genre", description = "Get all video games of the given genre")
	@Path("/{genre}/video-games")
	@Timed(name = "videogames-find-by-genre", absolute = true, description = "A measure of how long it takes to fetch all video games filtered by a given genre.", unit = MetricUnits.MILLISECONDS)
	public List<VideoGame> findByGenre(@PathParam("genre") final Genre genre) {
		log.debug("Calling {}.findByGenre : {}", this.getClass().getSimpleName(), genre);
		return videoGameRepository.findByGenre(genre);
	}

}
