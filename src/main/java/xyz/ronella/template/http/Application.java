package xyz.ronella.template.http;

import org.slf4j.LoggerFactory;
import xyz.ronella.logging.LoggerPlus;
import xyz.ronella.template.http.wrapper.SimpleHttpServer;

import java.io.IOException;

public class Application {

    private static final LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(Application.class));

    public static void main(String[] args) throws IOException {
        try(var mLOG = LOGGER_PLUS.groupLog("void main(String[])")) {
            var server = SimpleHttpServer.createServer();

            server.start();

            mLOG.info("\nThe app started on port 8080\nPress any key to stop...\n");
            System.in.read();

            server.stop();
        }
    }
}
