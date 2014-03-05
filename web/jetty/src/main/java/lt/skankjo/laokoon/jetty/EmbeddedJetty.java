package lt.skankjo.laokoon.jetty;

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbeddedJetty {

    private static final Logger logger = LoggerFactory.getLogger(EmbeddedJetty.class);

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        new EmbeddedJetty().runWar();
    }

    private void runWar() throws Exception {
        run(createWarContext());
    }

    private void run(WebAppContext context) throws Exception {
        if (System.getProperty("env") == null) {
            System.setProperty("env", "prod");
        }
        if (System.getProperty("application.home") == null) {
            System.setProperty("application.home", System.getProperty("user.home"));
        }

        int port = Integer.parseInt(System.getProperty("port", "8080"));
        Server server = new Server(port);

        Configuration.ClassList clist = Configuration.ClassList.setServerDefault(server);
        clist.addBefore(WebInfConfiguration.class.getName(), JettyWebXmlConfiguration.class.getName(), AnnotationConfiguration.class.getName());

        context.setContextPath(System.getProperty("ctxPath", "/"));
        context.setServer(server);

        server.setHandler(context);

        // allow services like message queues to shutdown gracefully
        server.setStopAtShutdown(true);
        logger.info("Starting embedded jetty server with env=" + System.getProperty("env"));
        server.start();
        server.join();
    }

    private WebAppContext createWarContext() {
        ProtectionDomain domain = EmbeddedJetty.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();

        WebAppContext webapp = new WebAppContext();
        webapp.setDescriptor(location.toExternalForm() + "/WEB-INF/web.xml");
        webapp.setWar(location.toExternalForm());
        webapp.setExtractWAR(false);

        webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", getPattern());
//        webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*\\.jar$|.*/classes/.*");

        if (System.getProperty("temp") != null) {
            File f = new File(System.getProperty("temp"));
            if (f.exists() && f.isDirectory()) {
                webapp.setTempDirectory(f);
            }
        }

        return webapp;
    }

    protected String getPattern() {
        return ".*";
    }
}
