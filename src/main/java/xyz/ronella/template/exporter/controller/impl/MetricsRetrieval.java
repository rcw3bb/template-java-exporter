package xyz.ronella.template.exporter.controller.impl;

import xyz.ronella.template.exporter.commons.ContentType;
import xyz.ronella.template.exporter.commons.ResponseStatus;
import xyz.ronella.template.exporter.config.AppConfig;
import xyz.ronella.template.exporter.config.MetricsModule;
import xyz.ronella.template.exporter.controller.IResource;
import xyz.ronella.template.exporter.service.IMetricsService;
import xyz.ronella.template.exporter.wrapper.SimpleHttpExchange;
import xyz.ronella.trivial.handy.RegExMatcher;

import static xyz.ronella.template.exporter.commons.Method.GET;

/**
 * A resource implementation for retrieving all metrics.
 *
 * @author Ron Webb
 * @since 1.0.0
 */
public class MetricsRetrieval implements IResource {
    @Override
    public boolean canProcess(SimpleHttpExchange simpleExchange) {
        final var method = simpleExchange.getRequestMethod().orElse(GET);
        final var pathMatches = RegExMatcher.find(getPathPattern(), simpleExchange.getRequestPath()).matches();
        return pathMatches && GET.equals(method);
    }

    @Override
    public void process(SimpleHttpExchange simpleExchange) {
        final var service = MetricsModule.getInstance(IMetricsService.class);
        final var response = service.getMetrics();
        simpleExchange.setResponseContentType(ContentType.TEXT_PLAIN);
        simpleExchange.sendResponseText(ResponseStatus.OK, response);
    }

    @Override
    public String getPathPattern() {
        return String.format("^%s$", AppConfig.INSTANCE.getBaseURL());
    }
}
