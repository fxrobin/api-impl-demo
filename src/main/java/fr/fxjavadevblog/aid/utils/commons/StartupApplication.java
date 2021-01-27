package fr.fxjavadevblog.aid.utils.commons;

import java.sql.SQLException;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import org.h2.tools.Server;

import io.quarkus.runtime.Startup;
import lombok.extern.slf4j.Slf4j;


@ApplicationScoped
@Startup
@Slf4j
public class StartupApplication {
	
	// embedding h2 webserver (web client) for dev purpose only.
	private Server h2Server;
	
	@PostConstruct
	public void init() throws SQLException
	{
		log.info("Application started");
		h2Server = Server.createWebServer();
		h2Server.start();
		log.info("H2 Web Server Started");
		log.info("H2 Port : {} . status {}", h2Server.getPort(), h2Server.getStatus());
	}
	
	@PreDestroy
	public void destroy()
	{		
		Optional.ofNullable(h2Server).ifPresent(Server::stop);
		log.info("H2 Server stopped");
	}

}
