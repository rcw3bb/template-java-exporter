package xyz.ronella.template.exporter;

import org.slf4j.LoggerFactory;
import xyz.ronella.logging.LoggerPlus;
import xyz.ronella.template.exporter.config.AppConfig;
import xyz.ronella.template.exporter.wrapper.SimpleHttpServer;

import java.io.IOException;

/**
 * The entry point of this application.
 *
 * @author Ron Webb
 * @since 1.0.0
 */
public class Application {

    private static final LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(Application.class));

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws IOException The exception to handle.
     */
    public static void main(String ... args) throws IOException {
        try(var mLOG = LOGGER_PLUS.groupLog("void main(String[])")) {
            final var server = SimpleHttpServer.createServer();
            final var port = AppConfig.INSTANCE.getServerPort();

            server.start();

            mLOG.info("\nThe app started on port " + port + "\nPress any key to stop...\n");
            System.in.read();

            server.stop();
        }
    }
}
