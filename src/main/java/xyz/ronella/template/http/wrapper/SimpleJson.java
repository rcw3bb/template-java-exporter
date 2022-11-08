package xyz.ronella.template.http.wrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleJson<T> {

    private final ObjectMapper objectMapper;

    public SimpleJson() {
        this.objectMapper = new ObjectMapper();
    }

    public String toJsonText(final T object) {
        String output = null;
        if (null != object) {
            try {
                output = objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException jpe) {
                throw new RuntimeException(jpe);
            }
        }
        return output;
    }

    public T toObjectType(final String json, final Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }

}
