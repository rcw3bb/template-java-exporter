package xyz.ronella.template.exporter.commons;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enum representing HTTP methods.
 *
 * @author Ron Webb
 * @since 1.0.0
 */
public enum Method {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE,
    COPY,
    HEAD,
    OPTIONS,
    LINK,
    UNLINK,
    PURGE,
    LOCK,
    UNLOCK,
    PROPFIND,
    VIEW;

    /**
     * Converts a string to its corresponding Method enum value.
     *
     * @param method The string representation of the HTTP method.
     * @return An Optional containing the corresponding Method enum value, or an empty Optional if no match is found.
     */
    public static Optional<Method> of(String method) {
        return Arrays.stream(Method.values()).filter(___type -> ___type.toString().equals(method)).findFirst();
    }

}
