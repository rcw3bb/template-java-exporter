package xyz.ronella.template.wrapper;

import org.junit.jupiter.api.*;
import xyz.ronella.template.exporter.wrapper.SimpleHttpServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SimpleHttpServerTest {

    private static SimpleHttpServer server = null;

    @BeforeAll
    public static void startServer() throws IOException {
        server = SimpleHttpServer.createServer();
        server.start();
        System.out.println("Server started");
    }

    @AfterAll
    public static void stopServer() {
        server.stop();
        System.out.println("Server stopped");
    }

    @Test
    @Order(10)
    public void retrieveMetrics() throws IOException, InterruptedException {
        final var client = HttpClient.newHttpClient();
        final var request = HttpRequest.newBuilder(URI.create("http://localhost:9000/metrics"))
                .GET()
                .build();
        final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        final var expected = """
                #Coming from a java-exporter default template.\r
                java_template_random_int \\d+""";
        assertTrue(response.body().matches(expected));
    }

}