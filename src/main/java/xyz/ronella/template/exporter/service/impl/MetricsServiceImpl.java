package xyz.ronella.template.exporter.service.impl;

import org.slf4j.LoggerFactory;
import xyz.ronella.logging.LoggerPlus;
import xyz.ronella.template.exporter.model.Metrics;
import xyz.ronella.template.exporter.service.IMetricsService;
import xyz.ronella.template.exporter.wrapper.SimpleTemplate;

import java.io.IOException;
import java.util.random.RandomGenerator;

/**
 * A simple implementation of a IMetricsService.
 * @author Ron Webb
 * @since 1.0.0
 */
public class MetricsServiceImpl implements IMetricsService {

    private static final LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(MetricsServiceImpl.class));
    private static final RandomGenerator randomGenerator = RandomGenerator.of("Random");

    /**
     * Must hold the implementation to return the metrics.
     * @return The metrics.
     */
    @Override
    public String getMetrics() {
        try(var mLOG = LOGGER_PLUS.groupLog("String getMetrics()")) {
            var metrics = new Metrics();
            metrics.setRandom(randomGenerator.nextInt(101));
            try {
                return SimpleTemplate.processTemplate("metrics.ftlt", metrics);
            } catch (IOException ioe) {
                mLOG.error(LOGGER_PLUS.getStackTraceAsString(ioe));
                throw new RuntimeException(ioe);
            }
        }
    }
}
