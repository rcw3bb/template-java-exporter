package xyz.ronella.template.exporter.config;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import xyz.ronella.template.exporter.controller.IResource;
import xyz.ronella.template.exporter.controller.IResources;
import xyz.ronella.template.exporter.controller.impl.*;
import xyz.ronella.template.exporter.service.IMetricsService;
import xyz.ronella.template.exporter.service.impl.MetricsServiceImpl;

/**
 * The configuration to wiring all Metrics related resources.
 * @author Ron Webb
 * @since 1.0.0
 */
final public class MetricsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IMetricsService.class)
                .annotatedWith(Names.named(MetricsResources.RESOURCE_NAME))
                .to(MetricsServiceImpl.class);
        bind(IResources.class)
                .annotatedWith(Names.named(MetricsResources.RESOURCE_NAME))
                .to(MetricsResources.class);
        final var resourceBinder = Multibinder.newSetBinder(binder(), IResource.class, Names.named(MetricsResources.RESOURCE_NAME));
        resourceBinder.addBinding().to(MetricsRetrieval.class);
    }

    private static Injector getInjector() {
        return Guice.createInjector(new MetricsModule());
    }

    /**
     * Returns an instance of the target interface that is fully wired.
     * @param clazz The target class.
     * @return An instance of the wired class.
     * @param <T> The target actual time.
     */
    public static <T> T getInstance(final Class<T> clazz) {
        return getInjector().getInstance(Key.get(clazz, Names.named(MetricsResources.RESOURCE_NAME)));
    }

}
