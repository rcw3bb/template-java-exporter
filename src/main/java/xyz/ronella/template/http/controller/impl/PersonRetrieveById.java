package xyz.ronella.template.http.controller.impl;

import xyz.ronella.template.http.commons.ResponseStatus;
import xyz.ronella.template.http.wrapper.SimpleHttpExchange;

import java.util.Optional;

public class PersonRetrieveById extends PersonRetrieveAll {

    public PersonRetrieveById() {
        super();
    }

    @Override
    public boolean canProcess(final SimpleHttpExchange simpleExchange) {
        return super.canProcess(simpleExchange);
    }

    @Override
    public void process(final SimpleHttpExchange simpleExchange) {
        final var matchId = getPathMatcher(simpleExchange).group(1);
        final var id = Long.valueOf(matchId);
        final var person = Optional.ofNullable(personService.findById(id));

        person.ifPresentOrElse(___person -> {
            final var response = personToJson(___person);
            simpleExchange.sendJsonResponse(response);
        }, () -> simpleExchange.sendResponseText(ResponseStatus.NOT_FOUND.getCode()));
    }

    @Override
    public String getPathPattern() {
        return String.format("^%s/person/(\\d*)$", getBaseURL());
    }
}
