package fr.fxjavadevblog.aid.utils;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.h2.tools.Server;

import io.quarkus.runtime.Startup;
import lombok.extern.slf4j.Slf4j;


@ApplicationScoped
@Startup
@Slf4j
public class StartupApplication {
	
	private Server h2Server;
	
	@PostConstruct
	public void init() throws SQLException
	{
		log.info("Application started");
		h2Server = Server.createWebServer();
		h2Server.start();
		log.info("H2 Started");
		log.info("H2 Port : {} . status {}", h2Server.getPort(), h2Server.getStatus());
	}

}
