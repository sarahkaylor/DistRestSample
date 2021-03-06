package SampleService;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/myapp/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in SampleService package
        final ResourceConfig rc = new ResourceConfig().packages("SampleService");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    
        int coreCount = Runtime.getRuntime().availableProcessors();
        
        ThreadPoolConfig config = ThreadPoolConfig.defaultConfig().copy();
        config.setPoolName("mypool")
              .setCorePoolSize(coreCount * 10)
              .setMaxPoolSize(coreCount * 20);

        for(NetworkListener listener : server.getListeners()) {
        	listener.getTransport().setWorkerThreadPoolConfig(config);
        }
        
        return server;
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

