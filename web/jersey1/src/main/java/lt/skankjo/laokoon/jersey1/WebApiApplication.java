package lt.skankjo.laokoon.jersey1;

import com.sun.jersey.api.core.PackagesResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Created by skankjo on 3/7/14.
 */
@ApplicationPath("api")
public class WebApiApplication extends PackagesResourceConfig{
    public WebApiApplication() {
        super("lt.skankjo.laokoon.api");
    }
}
