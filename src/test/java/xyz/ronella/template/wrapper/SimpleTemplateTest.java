package xyz.ronella.template.wrapper;

import org.junit.jupiter.api.Test;
import xyz.ronella.template.exporter.model.Metrics;
import xyz.ronella.template.exporter.wrapper.SimpleTemplate;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleTemplateTest {

    @Test
    public void processTemplate() throws IOException {
        var metrics = new Metrics();
        metrics.setRandom(123);
        try (final var writer = new StringWriter()) {
            SimpleTemplate.processTemplate("metrics.ftlt", metrics, writer);
            assertEquals("""
                    #Coming from a java-exporter default template.
                    java_template_random_int 123""", writer.toString());
        }
    }

    @Test
    public void processTemplateWithReturn() throws IOException {
        var metrics = new Metrics();
        metrics.setRandom(123);
        var output = SimpleTemplate.processTemplate("metrics.ftlt", metrics);
            assertEquals("""
                    #Coming from a java-exporter default template.
                    java_template_random_int 123""", output);
    }
}
