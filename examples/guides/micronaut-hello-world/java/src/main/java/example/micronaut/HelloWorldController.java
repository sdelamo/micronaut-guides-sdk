package example.micronaut;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.Collections;
import java.util.Map;

@Controller
class HelloWorldController {
    @Get
    Map<String, Object> index() {
        return Collections.singletonMap("message", "Hello World");
    }
}