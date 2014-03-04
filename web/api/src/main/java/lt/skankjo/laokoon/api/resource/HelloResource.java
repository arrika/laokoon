package lt.skankjo.laokoon.api.resource;

import jersey.repackaged.com.google.common.collect.Maps;
import org.glassfish.jersey.message.internal.MediaTypes;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by skankjo on 3/4/14.
 */
@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {

    @GET
    public Map<String, String> hello(@NotNull @QueryParam("who") String who) {
        String msg = String.format("Hello, %s", who == null ? "World" : who);
        Map<String, String> map = Maps.newHashMap();
        map.put("text", msg);
        return map;
    }
}
