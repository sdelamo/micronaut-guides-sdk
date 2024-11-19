package io.micronaut.guides.core;

import io.micronaut.core.annotation.NonNull;
import jakarta.validation.constraints.NotNull;

import java.io.File;
import java.io.IOException;

public interface WebsiteGenerator {
    void generate(
            @NonNull @NotNull File inputDirectory,
            @NonNull @NotNull File outputDirectory) throws IOException;
}
