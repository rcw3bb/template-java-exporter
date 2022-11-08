package xyz.ronella.template.http.controller.impl;

import xyz.ronella.template.http.wrapper.SimpleHttpExchange;

import static xyz.ronella.template.http.commons.ContentType.APPLICATION_JSON;
import static xyz.ronella.template.http.commons.Method.*;

public class PersonCreate extends AbstractPersonResource {

    public PersonCreate() {
        super();
    }

    @Override
    public boolean canProcess(final SimpleHttpExchange simpleExchange) {
        final var method = simpleExchange.getRequestMethod().orElse(GET);
        final var contentType = simpleExchange.getRequestContentType();

        return super.canProcess(simpleExchange) && POST.equals(method) && APPLICATION_JSON.equals(contentType.get());
    }

    @Override
    public void process(final SimpleHttpExchange simpleExchange) {
        final var payload = simpleExchange.getRequestPayload();
        final var person = jsonToPerson(payload);
        final var createdPerson = personService.create(person);
        final var response = personToJson(createdPerson);

        simpleExchange.sendJsonResponse(response);
    }

}
