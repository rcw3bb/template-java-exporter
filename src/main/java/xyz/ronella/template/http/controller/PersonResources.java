package xyz.ronella.template.http.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.slf4j.LoggerFactory;
import xyz.ronella.logging.LoggerPlus;
import xyz.ronella.template.http.config.PersonModule;
import xyz.ronella.template.http.wrapper.SimpleHttpExchange;

import java.util.Optional;
import java.util.Set;

public class PersonResources implements IResources {

    private static final LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(PersonResources.class));

    public static final String RESOURCE_NAME = "Person";

    final private Set<IResource> resources;

    @Inject
    public PersonResources(@Named("Person") final Set<IResource> resources) {
        this.resources = resources;
    }

    @Override
    public Set<IResource> getResources() {
        return resources;
    }

    public static Optional<IResource> getInstance(SimpleHttpExchange simpleHttpExchange) {
        try(var mLOG = LOGGER_PLUS.groupLog("Optional<IResource> getInstance(SimpleHttpExchange)")) {
            final var personResource = PersonModule.getInstance(IResources.class);
            final var resources = personResource.getResources();
            final var resource = resources.stream().filter(___resource -> ___resource.canProcess(simpleHttpExchange)).findFirst();
            mLOG.debug(()-> "Resource instance: " + resource.get());
            return resource;
        }
    }

}
