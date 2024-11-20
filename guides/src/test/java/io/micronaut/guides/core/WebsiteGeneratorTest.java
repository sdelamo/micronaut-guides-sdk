package io.micronaut.guides.core;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

@MicronautTest(startApplication = false)
public class WebsiteGeneratorTest {

    @Inject
    WebsiteGenerator websiteGenerator;

    @Test
    void testWebsiteGenerator() throws IOException {
        File input = new File("/Users/anlagrot/micronaut/micronaut-guides-sdk/examples");
        File output = new File("/Users/anlagrot/micronaut/micronaut-guides-sdk/build");
        websiteGenerator.generate(input, output);
    }

}
