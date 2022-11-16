package xyz.ronella.template.http.controller.impl;

import xyz.ronella.template.http.commons.ResponseStatus;
import xyz.ronella.template.http.wrapper.SimpleHttpExchange;

import java.util.Optional;

import static xyz.ronella.template.http.commons.ContentType.APPLICATION_JSON;
import static xyz.ronella.template.http.commons.Method.*;

/**
 * A resource implementation for updating a Person by ID.
 *
 * @author Ron Webb
 * @since 1.0.0
 */
public class PersonUpdateById extends AbstractPersonResource {

    /**
     * Creates an instance of PersonUpdateById.
     */
    public PersonUpdateById() {
        super();
    }

    /**
     * The logic for declaring that it can do processing for updating a Person by ID.
     * @param simpleExchange An instance of SimpleHttpExchange.
     * @return Returns true to process.
     */
    @Override
    public boolean canProcess(final SimpleHttpExchange simpleExchange) {
        final var method = simpleExchange.getRequestMethod().orElse(GET);
        final var contentType = simpleExchange.getRequestContentType();

        return super.canProcess(simpleExchange) && PUT.equals(method) && APPLICATION_JSON.equals(contentType.get());
    }

    /**
     * The logic for updating a Person resource by ID.
     * @param simpleExchange An instance of SimpleHttpExchange.
     */
    @Override
    public void process(final SimpleHttpExchange simpleExchange) {
            final var payload = simpleExchange.getRequestPayload();
            final var person = jsonToPerson(payload);

            final var personFound = Optional.ofNullable(getService().findById(person.getId()));

            personFound.ifPresentOrElse(___person -> {
                final var updatedPerson = getService().update(person);
                final var response = personToJson(updatedPerson);

                simpleExchange.sendJsonResponse(response);
            }, ()-> simpleExchange.sendResponseCode(ResponseStatus.NOT_FOUND));
    }

}
