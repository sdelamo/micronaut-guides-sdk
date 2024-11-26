package io.micronaut.guides.cli;

import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.io.ResourceLoader;
import io.micronaut.guides.core.Guide;
import io.micronaut.guides.core.GuidesOption;
import io.micronaut.guides.core.GuidesTemplatesConfiguration;
import io.micronaut.guides.core.MacroSubstitution;
import io.micronaut.guides.core.asciidoc.AsciidocMacro;
import io.micronaut.guides.core.asciidoc.AsciidocUtils;
import io.micronaut.starter.options.BuildTool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.micronaut.guides.core.MacroUtils.findMacroLines;

abstract class GradleMavenTabs implements MacroSubstitution {
    protected String gradleMavenTabsHtml;

    @NonNull
    protected abstract String getMacro();

    @NonNull
    protected abstract String gradle(AsciidocMacro asciidocMacro, Guide guide, GuidesOption option);

    @NonNull
    protected abstract String maven(AsciidocMacro asciidocMacro, Guide guide, GuidesOption option);

    @Override
    public String substitute(String str, Guide guide, GuidesOption option) {
        for (String line : findMacroLines(str, getMacro())) {
            Optional<AsciidocMacro> asciidocMacroOptional = AsciidocMacro.of(getMacro(), line);
            if (asciidocMacroOptional.isEmpty()) {
                continue;
            }
            str = str.replace(line, AsciidocUtils.passthroughBlock(gradleMavenTabsHtml.replace("{gradle}", gradle(asciidocMacroOptional.get(), guide, option))
                    .replace("{maven}", maven(asciidocMacroOptional.get(), guide, option))));
        }
        return str;
    }

    protected GradleMavenTabs(ResourceLoader resourceLoader, GuidesTemplatesConfiguration guidesTemplatesConfiguration) {
        String path = "classpath:" + guidesTemplatesConfiguration.getFolder() + "/gradleMavenTabs.html";
        Optional<InputStream> inputStreamOptional = resourceLoader.getResourceAsStream(path);
        if (inputStreamOptional.isEmpty()) {
            throw new ConfigurationException(path);
        }
        try (InputStream inputStream = inputStreamOptional.get()) {
            this.gradleMavenTabsHtml = readInputStream(inputStream);
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
