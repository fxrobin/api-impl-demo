package fr.fxjavadevblog.aid;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

@OpenAPIDefinition(
  info = @Info(
          title = "Simple API over video games catalog of ATARI ST",
          version = "1.0.0",
          description = "Give read access to every video games for the ATARI ST",
          license = @License(name = "Apache 2.0", url = "https://www.fxjavadevblog.fr"),
          contact = @Contact(url = "https://www.fxjavadevblog.fr", name = "François-Xavier Robin", email = "webmaster@fxjavadevblog.fr")
  )
)
public class ApiDefinition extends Application
{

}
