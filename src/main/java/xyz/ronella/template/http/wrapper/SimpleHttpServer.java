package xyz.ronella.template.http.wrapper;

import com.sun.net.httpserver.HttpServer;

import xyz.ronella.template.http.commons.ResponseStatus;
import xyz.ronella.template.http.config.AppConfig;
import xyz.ronella.template.http.controller.PersonResources;

import java.io.IOException;
import java.net.InetSocketAddress;

final public class SimpleHttpServer {

    private static final AppConfig CONFIG= AppConfig.INSTANCE;

    private final HttpServer httpServer;

    private SimpleHttpServer() throws IOException {
        final var baseUrl = CONFIG.getBaseURL();
        httpServer = HttpServer.create(new InetSocketAddress(CONFIG.getServerPort()), 0);
        final var context = httpServer.createContext(baseUrl.isEmpty() ? "/" : baseUrl);

        context.setHandler(___exchange -> {
            final var simpleExchange = new SimpleHttpExchange(___exchange);
            final var resource = PersonResources.getInstance(simpleExchange);

            resource.ifPresentOrElse(___resource -> resource.get().process(simpleExchange),
                    () -> simpleExchange.sendResponseText(ResponseStatus.NO_CONTENT.getCode()));
        });
    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(1);
    }

    public static SimpleHttpServer createServer() throws IOException {
        return new SimpleHttpServer();
    }

}
