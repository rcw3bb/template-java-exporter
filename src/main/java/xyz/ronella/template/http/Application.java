package xyz.ronella.template.http;

import com.sun.net.httpserver.HttpServer;

import xyz.ronella.template.http.commons.ResponseStatus;
import xyz.ronella.template.http.config.AppConfig;
import xyz.ronella.template.http.controller.PersonResources;
import xyz.ronella.template.http.wrapper.SimpleHttpExchange;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Application {

    private static final AppConfig CONFIG= AppConfig.INSTANCE;

    public static void main(String[] args) throws IOException {

        var baseUrl = CONFIG.getBaseURL();
        var server = HttpServer.create(new InetSocketAddress(CONFIG.getServerPort()), 0);
        var context = server.createContext(baseUrl.isEmpty() ? "/" : baseUrl);

        context.setHandler(___exchange -> {
            final var simpleExchange = new SimpleHttpExchange(___exchange);
            final var resource = PersonResources.getInstance(simpleExchange);

            resource.ifPresentOrElse(___resource -> resource.get().process(simpleExchange),
                    () -> simpleExchange.sendResponseText(ResponseStatus.NO_CONTENT.getCode()));
        });

        server.start();

        System.out.printf("The app started on port 8080%nPress any key to stop...%n");
        System.in.read();
        server.stop(0);
    }
}
