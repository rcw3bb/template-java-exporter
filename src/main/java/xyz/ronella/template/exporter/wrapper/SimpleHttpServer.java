package xyz.ronella.template.exporter.wrapper;

import com.sun.net.httpserver.HttpServer;

import xyz.ronella.template.exporter.commons.ResponseStatus;
import xyz.ronella.template.exporter.config.AppConfig;
import xyz.ronella.template.exporter.controller.impl.MetricsResources;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * The class that only knows about the HttpServer.
 *
 * @author Ron Webb
 * @since 1.0.0
 */
final public class SimpleHttpServer implements Closeable {

    private static final AppConfig CONFIG= AppConfig.INSTANCE;

    private final HttpServer httpServer;

    private SimpleHttpServer() throws IOException {
        final var defaultContext = "/";
        final var baseUrl = CONFIG.getBaseURL();
        httpServer = HttpServer.create(new InetSocketAddress(CONFIG.getServerPort()), 0);
        final var context = httpServer.createContext(baseUrl.isEmpty() ? defaultContext : baseUrl);

        context.setHandler(___exchange -> {
            final var simpleExchange = new SimpleHttpExchange(___exchange);
            final var resource = MetricsResources.createResource(simpleExchange);

            resource.ifPresentOrElse(___resource -> resource.get().process(simpleExchange),
                    () -> simpleExchange.sendResponseCode(ResponseStatus.NO_CONTENT));
        });
    }

    /**
     * Starts the server.
     */
    public void start() {
        httpServer.start();
    }

    /**
     * Stops the server.
     */
    public void stop() {
        httpServer.stop(1);
    }

    /**
     * Create an instance of SimpleHttpServer.
     * @return An instance of SimpleHttpServer.
     * @throws IOException The exception that must be handled.
     */
    public static SimpleHttpServer createServer() throws IOException {
        return new SimpleHttpServer();
    }

    @Override
    public void close() throws IOException {
        stop();
    }
}
