package fr.fxjavadevblog.aid.videogame;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(hal = true, path = "/api/v1/jeux")
public interface JeuxVideoResource extends PanacheRepositoryResource<VideoGameRepository, VideoGame, String> {
	
	
}
