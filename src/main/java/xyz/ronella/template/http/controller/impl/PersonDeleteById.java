package xyz.ronella.template.http.controller.impl;

import xyz.ronella.template.http.commons.ResponseStatus;
import xyz.ronella.template.http.wrapper.SimpleHttpExchange;

import java.util.Optional;

import static xyz.ronella.template.http.commons.Method.DELETE;
import static xyz.ronella.template.http.commons.Method.GET;

public class PersonDeleteById extends AbstractPersonResource {

    public PersonDeleteById() {
        super();
    }

    @Override
    public boolean canProcess(final SimpleHttpExchange simpleExchange) {
        final var method = simpleExchange.getRequestMethod().orElse(GET);
        return super.canProcess(simpleExchange) && DELETE.equals(method);
    }

    @Override
    public void process(final SimpleHttpExchange simpleExchange) {
        final var matchId = getPathMatcher(simpleExchange).group(1);
        final var id = Long.valueOf(matchId);
        final var person = Optional.ofNullable(personService.findById(id));

        person.ifPresentOrElse(___person -> {
            personService.delete(___person.getId());
            simpleExchange.sendResponseText(ResponseStatus.OK.getCode());
        }, () -> simpleExchange.sendResponseText(ResponseStatus.NOT_FOUND.getCode()));
    }

    @Override
    public String getPathPattern() {
        return "^/person/(\\d*)$";
    }
}
