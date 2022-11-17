package xyz.ronella.template.api.controller.impl;

import xyz.ronella.template.api.wrapper.SimpleHttpExchange;

import static xyz.ronella.template.api.commons.ContentType.APPLICATION_JSON;
import static xyz.ronella.template.api.commons.Method.*;

/**
 * A resource implementation for creating a Person.
 *
 * @author Ron Webb
 * @since 1.0.0
 */
public class PersonCreate extends AbstractPersonResource {

    /**
     * Creates an instance of PersonCreate.
     */
    public PersonCreate() {
        super();
    }

    /**
     * The logic for declaring that it can do processing for creating a Person resource.
     * @param simpleExchange An instance of SimpleHttpExchange.
     * @return Returns true to process.
     */
    @Override
    public boolean canProcess(final SimpleHttpExchange simpleExchange) {
        final var method = simpleExchange.getRequestMethod().orElse(GET);
        final var contentType = simpleExchange.getRequestContentType();

        return super.canProcess(simpleExchange) && POST.equals(method) && APPLICATION_JSON.equals(contentType.get());
    }

    /**
     * The logic for creating a Person resource.
     * @param simpleExchange An instance of SimpleHttpExchange.
     */
    @Override
    public void process(final SimpleHttpExchange simpleExchange) {
        final var payload = simpleExchange.getRequestPayload();
        final var person = jsonToPerson(payload);
        final var createdPerson = getService().create(person);
        final var response = personToJson(createdPerson);

        simpleExchange.sendJsonResponse(response);
    }

}
