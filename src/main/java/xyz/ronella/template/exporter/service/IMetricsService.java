package xyz.ronella.template.exporter.service;

/**
 * Must hold the business logic for metrics resource.
 *
 * @author Ron Webb
 * @since 1.0.0
 */
public interface IMetricsService {

    /**
     * Must hold the implementation to return the metrics.
     * @return The metrics.
     */
    String getMetrics();
}
