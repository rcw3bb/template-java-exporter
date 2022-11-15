package xyz.ronella.template.http.wrapper;

import com.sun.net.httpserver.HttpExchange;

import org.slf4j.LoggerFactory;
import xyz.ronella.logging.LoggerPlus;
import xyz.ronella.template.http.commons.ContentType;
import xyz.ronella.template.http.commons.Method;
import xyz.ronella.template.http.commons.ResponseStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Optional;

public class SimpleHttpExchange {

    private static final LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(SimpleHttpExchange.class));

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
        try(var mLOG = LOGGER_PLUS.groupLog("void sendResponseText(int,String)")) {
            mLOG.debug(() -> String.format("Response Code: %s; Response Text: %s", responseCode, responseText));
            try {
                final var responseBytes = responseText.getBytes();
                final var contentLength = responseBytes.length;

                exchange.sendResponseHeaders(responseCode, contentLength);

                if (ResponseStatus.NO_CONTENT.getCode() != responseCode) {
                    try (final var os = exchange.getResponseBody()) {
                        os.write(responseBytes);
                    }
                }

            } catch (IOException ioe) {
                mLOG.error(LOGGER_PLUS.getStackTraceAsString(ioe));
                throw new RuntimeException(ioe);
            }
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
        try(var mLOG = LOGGER_PLUS.groupLog("String getRequestPayload()")) {
            String payload;

            try (final var httpInput = new BufferedReader(new InputStreamReader(
                    exchange.getRequestBody()))) {

                final var in = new StringBuilder();

                String input;
                while ((input = httpInput.readLine()) != null) {
                    in.append(input).append(" ");
                }

                mLOG.debug(() -> "Request Payload: " + in.toString().trim());
                payload = in.toString().trim();
            } catch (IOException ioe) {
                mLOG.error(LOGGER_PLUS.getStackTraceAsString(ioe));
                throw new RuntimeException(ioe);
            }

            return payload;
        }
    }

}
