package xyz.ronella.template.http.controller.impl;

import xyz.ronella.template.http.wrapper.SimpleHttpExchange;

import static xyz.ronella.template.http.commons.Method.GET;

public class PersonRetrieveAll extends AbstractPersonResource {

    public PersonRetrieveAll() {
        super();
    }

    @Override
    public boolean canProcess(final SimpleHttpExchange simpleExchange) {
        final var method = simpleExchange.getRequestMethod().orElse(GET);
        return super.canProcess(simpleExchange) && GET.equals(method);
    }

    @Override
    public void process(final SimpleHttpExchange simpleExchange) {
        final var persons = personService.findAll();
        final var response = personListToJson(persons);

        simpleExchange.sendJsonResponse(response);
    }

}
