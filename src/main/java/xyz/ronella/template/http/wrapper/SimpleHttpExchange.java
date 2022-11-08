package xyz.ronella.template.http.wrapper;

import com.sun.net.httpserver.HttpExchange;

import xyz.ronella.template.http.commons.ContentType;
import xyz.ronella.template.http.commons.Method;
import xyz.ronella.template.http.commons.ResponseStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Optional;

public class SimpleHttpExchange {

    protected final HttpExchange exchange;
    public SimpleHttpExchange(final HttpExchange exchange) {
        this.exchange = exchange;
    }

    public HttpExchange getHttpExchange() {
        return exchange;
    }

    protected URI getRequestURI() {
        return exchange.getRequestURI();
    }

    public String getRequestPath() {
        return getRequestURI().getPath();
    }

    public Optional<Method> getRequestMethod() {
        return Method.of(exchange.getRequestMethod());
    }

    public String getRequestQuery() {
        return getRequestURI().getQuery();
    }

    public Optional<ContentType> getRequestContentType() {
        return ContentType.of(exchange.getRequestHeaders().getFirst("Content-type"));
    }

    public void sendResponseText(final int responseCode, final String responseText) {
        final var responseBytes = responseText.getBytes();
        try {
            exchange.sendResponseHeaders(responseCode, responseBytes.length);
            try (var os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public void sendResponseText(final int responseCode) {
        sendResponseText(responseCode, "");
    }

    public void sendJsonResponse(String jsonResponse) {
        setResponseContentType(ContentType.APPLICATION_JSON);
        sendResponseText(ResponseStatus.OK.getCode(), jsonResponse);
    }

    public void setResponseContentType(final ContentType contentType) {
        final var headers = exchange.getResponseHeaders();
        headers.add("Content-type", contentType.toString());
    }

    public String getRequestPayload() {
        String payload = null;

        try (final var httpInput = new BufferedReader(new InputStreamReader(
                exchange.getRequestBody()))) {

            final var in = new StringBuilder();

            String input;
            while ((input = httpInput.readLine()) != null) {
                in.append(input).append(" ");
            }

            payload = in.toString().trim();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

        return payload;
    }

}
