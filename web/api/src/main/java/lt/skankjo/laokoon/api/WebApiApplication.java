package lt.skankjo.laokoon.api;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class WebApiApplication extends ResourceConfig {
    public WebApiApplication() {
        packages("lt.skankjo.laokoon.api");
    }
}
