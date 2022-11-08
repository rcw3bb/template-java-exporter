package xyz.ronella.template.http.controller.impl;

import xyz.ronella.template.http.controller.IResource;
import xyz.ronella.template.http.ioc.PersonModule;
import xyz.ronella.template.http.model.Person;
import xyz.ronella.template.http.service.IPersonService;
import xyz.ronella.template.http.wrapper.SimpleHttpExchange;

import xyz.ronella.template.http.wrapper.SimpleJson;
import xyz.ronella.trivial.handy.RegExMatcher;

import java.util.List;
import java.util.regex.Matcher;

public abstract class AbstractPersonResource implements IResource {

    protected IPersonService personService;
    private Matcher pathMatcher;

    public AbstractPersonResource() {
        this.personService = PersonModule.getInstance(IPersonService.class);
    }

    protected Matcher getPathMatcher(final SimpleHttpExchange simpleExchange) {
        if (null == pathMatcher) {
            pathMatcher = RegExMatcher.find(getPathPattern(), simpleExchange.getRequestPath());
        }

        return pathMatcher;
    }

    @Override
    public boolean canProcess(final SimpleHttpExchange simpleExchange) {
        return getPathMatcher(simpleExchange).matches();
    }

    @Override
    public String getPathPattern() {
        return "^/person$";
    }

    public String personToJson(Person person) {
        final var mapper = new SimpleJson<Person>();
        return mapper.toJsonText(person);
    }

    public String personListToJson(final List<Person> persons) {
        final var mapper = new SimpleJson<List<Person>>();
        return mapper.toJsonText(persons);
    }

    public Person jsonToPerson(String json) {
        final var simpleJson = new SimpleJson<Person>();
        return simpleJson.toObjectType(json, Person.class);
    }
}
