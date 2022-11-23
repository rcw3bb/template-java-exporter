package xyz.ronella.template.exporter.controller.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.LoggerFactory;
import xyz.ronella.logging.LoggerPlus;
import xyz.ronella.template.exporter.config.MetricsModule;
import xyz.ronella.template.exporter.controller.IResource;
import xyz.ronella.template.exporter.controller.IResources;
import xyz.ronella.template.exporter.wrapper.SimpleHttpExchange;

import java.util.Optional;
import java.util.Set;

/**
 * The implementation for IResources for returning a particular implementation of IResource.
 *
 * @author Ron Webb
 * @since 1.0.0
 */
public class MetricsResources implements IResources {
    private static final LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(MetricsResources.class));

    /**
     * The resource name for Metrics.
     */
    public static final String RESOURCE_NAME = "Metrics";

    final private Set<IResource> resources;

    /**
     * Creates an instance of MetricsResources.
     * @param resources An set of unique implementation of IResource.
     */
    @Inject
    public MetricsResources(@Named(RESOURCE_NAME) final Set<IResource> resources) {
        this.resources = resources;
    }

    /**
     * A set of unique implementation if IResource.
     * @return A set of IResource.
     */
    @Override
    public Set<IResource> getResources() {
        return resources;
    }

    /**
     * Creates a particular implementation IResource.
     * @param exchange An instance of SimpleHttpExchange.
     * @return An implementation of IResource.
     */
    public static Optional<IResource> createResource(SimpleHttpExchange exchange) {
        try(var mLOG = LOGGER_PLUS.groupLog("Optional<IResource> getInstance(SimpleHttpExchange)")) {
            final var metricsResource = MetricsModule.getInstance(IResources.class);
            final var resources = metricsResource.getResources();
            final var resource = resources.stream().filter(___resource -> ___resource.canProcess(exchange)).findFirst();
            mLOG.debug(()-> "Resource instance: " + resource.get());
            return resource;
        }
    }

}
