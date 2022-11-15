package xyz.ronella.template.http.config;

import org.slf4j.LoggerFactory;
import xyz.ronella.logging.LoggerPlus;
import xyz.ronella.trivial.handy.PathFinder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

final public class AppConfig {

    private static final LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(AppConfig.class));
    public final static AppConfig INSTANCE = new AppConfig();

    final private ResourceBundle prop;

    private AppConfig() {
        try {
            final var confName = "application.properties";
            final var locations = List.of("../conf", "conf");
            final var confFound = PathFinder.getBuilder(confName).addPaths(locations).build().getFile();
            final var propFile = confFound.get();

            final var versionProp = new FileInputStream(propFile);
            this.prop = new PropertyResourceBundle(versionProp);
        } catch (IOException exp) {
            LOGGER_PLUS.error(LOGGER_PLUS.getStackTraceAsString(exp));
            throw new RuntimeException(exp);
        }
    }

    public int getServerPort() {
        final var port = prop.getString("server.port");
        return Integer.parseInt(port);
    }

    public String getBaseURL() {
        return prop.getString("base.url").trim();
    }
}