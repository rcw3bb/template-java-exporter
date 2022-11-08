package xyz.ronella.template.http.controller.impl;

import xyz.ronella.template.http.commons.ResponseStatus;
import xyz.ronella.template.http.wrapper.SimpleHttpExchange;

import java.util.Optional;

import static xyz.ronella.template.http.commons.ContentType.APPLICATION_JSON;
import static xyz.ronella.template.http.commons.Method.*;

public class PersonUpdateById extends AbstractPersonResource {

    public PersonUpdateById() {
        super();
    }

    @Override
    public boolean canProcess(final SimpleHttpExchange simpleExchange) {
        final var method = simpleExchange.getRequestMethod().orElse(GET);
        final var contentType = simpleExchange.getRequestContentType();

        return super.canProcess(simpleExchange) && PUT.equals(method) && APPLICATION_JSON.equals(contentType.get());
    }

    @Override
    public void process(final SimpleHttpExchange simpleExchange) {
            final var payload = simpleExchange.getRequestPayload();
            final var person = jsonToPerson(payload);

            final var personFound = Optional.ofNullable(personService.findById(person.getId()));

            personFound.ifPresentOrElse(___person -> {
                final var updatedPerson = personService.update(person);
                final var response = personToJson(updatedPerson);

                simpleExchange.sendJsonResponse(response);
            }, ()-> simpleExchange.sendResponseText(ResponseStatus.NO_CONTENT.getCode()));
    }

}
