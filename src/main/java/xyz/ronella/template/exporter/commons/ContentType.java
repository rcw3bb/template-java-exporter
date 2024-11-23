package xyz.ronella.template.exporter.commons;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enum representing various content types.
 *
 * @author Ron Webb
 * @since 1.0.0
 */
public enum ContentType {
    APP_ATOM_XML("application/atom+xml"),
    APP_FORM_URLENC("application/x-www-form-urlencoded"),
    APP_JSON("application/json"),
    APP_JSN_PTCH_JSN("application/json-patch+json"),
    APP_OCTET_STREAM("application/octet-stream"),
    APP_SVG_XML("application/svg+xml"),
    APP_XHTML_XML("application/xhtml+xml"),
    APP_XML("application/xml"),
    MULTI_FORM_DATA("multipart/form-data"),
    TEXT_EVENT_STREAM("text/event-stream"),
    TEXT_HTML("text/html"),
    TEXT_PLAIN("text/plain"),
    TEXT_XML("text/xml"),
    WILDCARD("*/*");

    private final String text;

    /**
     * Constructor for ContentType enum.
     *
     * @param text The string representation of the content type.
     */
    ContentType(String text) {
        this.text = text;
    }

    /**
     * Returns the string representation of the content type.
     *
     * @return The string representation of the content type.
     */
    @Override
    public String toString() {
        return text;
    }

    /**
     * Retrieves the ContentType enum constant corresponding to the given string.
     *
     * @param contentType The string representation of the content type.
     * @return An Optional containing the matching ContentType, or an empty Optional if no match is found.
     */
    public static Optional<ContentType> of(String contentType) {
        return Arrays.stream(ContentType.values()).filter(___type -> ___type.toString().equals(contentType)).findFirst();
    }
}
