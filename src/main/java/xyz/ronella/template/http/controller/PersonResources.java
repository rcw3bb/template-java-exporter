package xyz.ronella.template.http.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import xyz.ronella.template.http.config.PersonModule;
import xyz.ronella.template.http.wrapper.SimpleHttpExchange;

import java.util.Optional;
import java.util.Set;

public class PersonResources implements IResources {

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
        final var personResource = PersonModule.getInstance(IResources.class);
        final var resources = personResource.getResources();
        return resources.stream().filter(___resource -> ___resource.canProcess(simpleHttpExchange)).findFirst();
    }

}
