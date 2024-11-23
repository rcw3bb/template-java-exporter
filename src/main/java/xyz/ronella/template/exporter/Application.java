package xyz.ronella.template.exporter;

import org.slf4j.LoggerFactory;
import xyz.ronella.logging.LoggerPlus;
import xyz.ronella.template.exporter.config.AppConfig;
import xyz.ronella.template.exporter.util.AppInfo;
import xyz.ronella.template.exporter.wrapper.SimpleHttpServer;
import xyz.ronella.trivial.handy.PathFinder;

import java.io.IOException;

/**
 * The entry point of this application.
 *
 * @author Ron Webb
 * @since 1.0.0
 */
public class Application {

    static {
        final var userDir = "user.dir";
        final var confDir = System.getProperty(userDir) + "/conf";
        final var logPath = PathFinder.getBuilder("logback.xml")
                .addSysProps(userDir)
                .addPaths(confDir, "..", "../conf")
                .build();
        final var optLogFile = logPath.getFile();

        if (optLogFile.isPresent()) {
            final var logSysProp = "logback.configurationFile";
            final var logFile = optLogFile.get().getAbsolutePath();
            System.out.printf("%s: %s%n", logSysProp, logFile);
            System.setProperty(logSysProp, logFile);
        }
    }

    private static final LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(Application.class));

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws IOException The exception to handle.
     */
    public static void main(String ... args) throws IOException {
        try(var mLOG = LOGGER_PLUS.groupLog("void main(String[])")) {
            final var appInfo = AppInfo.INSTANCE;
            final var header = String.format("%s v%s (%s)"
                    , appInfo.getAppName()
                    , appInfo.getAppVersion()
                    , appInfo.getBuildDate()
            );
            mLOG.info(header);
            mLOG.info("Working Directory: %s", System.getProperty("user.dir"));

            try(final var server = SimpleHttpServer.createServer()) {
                final var port = AppConfig.INSTANCE.getServerPort();

                server.start();
                mLOG.info("%nThe app started on port %s%nPress <ENTER> to stop...%n", port);
                System.in.read();
            }
        }
    }
}
