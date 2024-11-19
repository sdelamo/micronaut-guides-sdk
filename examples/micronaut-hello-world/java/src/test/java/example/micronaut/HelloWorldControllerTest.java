package example.micronaut;

import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class HelloWorldControllerTest {

    @Test
    void helloWorld(@Client("/")HttpClient httpClient) {
        BlockingHttpClient client = httpClient.toBlocking();
        String json = assertDoesNotThrow(() -> client.retrieve("/", String.class));
        assertEquals("{\"message\":\"Hello World\"}", json);
    }
}