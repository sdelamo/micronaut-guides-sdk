package io.micronaut.guides.cli;

import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.core.io.ResourceLoader;
import io.micronaut.guides.core.GuidesTemplatesConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConsoleTabs {
    protected String consoleTabsHtml;

    protected ConsoleTabs(ResourceLoader resourceLoader, GuidesTemplatesConfiguration guidesTemplatesConfiguration) {
        String path = "classpath:" + guidesTemplatesConfiguration.getFolder() + "/consoleTabs.html";
        Optional<InputStream> inputStreamOptional = resourceLoader.getResourceAsStream(path);
        if (inputStreamOptional.isEmpty()) {
            throw new ConfigurationException(path);
        }
        try (InputStream inputStream = inputStreamOptional.get()) {
            this.consoleTabsHtml = readInputStream(inputStream);
        } catch (Exception e) {
            throw new ConfigurationException("Error loading resource: " + path, e);
        }
    }

    protected static String readInputStream(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
