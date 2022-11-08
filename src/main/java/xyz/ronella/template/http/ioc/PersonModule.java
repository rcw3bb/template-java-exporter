package xyz.ronella.template.http.ioc;

import com.google.inject.AbstractModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

import xyz.ronella.template.http.controller.IResource;
import xyz.ronella.template.http.controller.IResources;
import xyz.ronella.template.http.controller.PersonResources;
import xyz.ronella.template.http.controller.impl.*;
import xyz.ronella.template.http.repository.IPersonRepository;
import xyz.ronella.template.http.repository.impl.PersonListRepository;
import xyz.ronella.template.http.service.IPersonService;
import xyz.ronella.template.http.service.impl.PersonServiceImpl;

final public class PersonModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IPersonService.class)
                .annotatedWith(Names.named(PersonResources.RESOURCE_NAME))
                .to(PersonServiceImpl.class);
        bind(IPersonRepository.class)
                .annotatedWith(Names.named(PersonResources.RESOURCE_NAME))
                .to(PersonListRepository.class);
        bind(IResources.class)
                .annotatedWith(Names.named(PersonResources.RESOURCE_NAME))
                .to(PersonResources.class);

        final var resourceBinder = Multibinder.newSetBinder(binder(), IResource.class, Names.named(PersonResources.RESOURCE_NAME));
        resourceBinder.addBinding().to(PersonCreate.class);
        resourceBinder.addBinding().to(PersonDeleteById.class);
        resourceBinder.addBinding().to(PersonRetrieveAll.class);
        resourceBinder.addBinding().to(PersonRetrieveById.class);
        resourceBinder.addBinding().to(PersonUpdateById.class);
    }

    private static Injector getInjector() {
        return Guice.createInjector(new PersonModule());
    }

    public static <T> T getInstance(final Class<T> clazz) {
        return getInjector().getInstance(Key.get(clazz, Names.named(PersonResources.RESOURCE_NAME)));
    }

}
