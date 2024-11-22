/*
 * Copyright 2017-2024 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.guides.core;

import com.networknt.schema.InputFormat;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.type.Argument;
import io.micronaut.json.JsonMapper;
import jakarta.inject.Singleton;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static io.micronaut.guides.core.GuideUtils.mergeMetadataList;

/**
 * Class that provides methods to parse guide metadata.
 */
@Singleton
public class DefaultGuideParser implements GuideParser {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultGuideParser.class);

    private final JsonSchema jsonSchema;
    private final JsonMapper jsonMapper;

    /**
     * Constructs a new DefaultGuideParser.
     *
     * @param jsonSchemaProvider the JSON schema provider
     * @param jsonMapper         the JSON mapper
     */
    public DefaultGuideParser(JsonSchemaProvider jsonSchemaProvider, JsonMapper jsonMapper) {
        this.jsonSchema = jsonSchemaProvider.getSchema();
        this.jsonMapper = jsonMapper;
    }

    @Override
    @NonNull
    public List<Guide> parseGuidesMetadata(@NonNull @NotNull File guidesDir, @NonNull @NotNull String metadataConfigName) {
        List<Guide> metadatas = new ArrayList<>();

        File[] dirs = guidesDir.listFiles(File::isDirectory);
        if (dirs == null) {
            return metadatas;
        }
        for (File dir : dirs) {
            parseGuideMetadata(dir, metadataConfigName).ifPresent(metadatas::add);
        }

        mergeMetadataList(metadatas);

        return metadatas;
    }

    @Override
    @NonNull
    public Optional<Guide> parseGuideMetadata(@NonNull @NotNull File guidesDir, @NonNull @NotNull String metadataConfigName) {
        File configFile = new File(guidesDir, metadataConfigName);
        if (!configFile.exists()) {
            LOG.warn("metadata file not found for {}", guidesDir.getName());
            return Optional.empty();
        }

        String content;
        try {
            content = Files.readString(Paths.get(configFile.toString()));
        } catch (IOException e) {
            LOG.warn("metadata file not found for {}", guidesDir.getName());
            return Optional.empty();
        }

        Map<String, Object> config;
        try {
            config = jsonMapper.readValue(content, Argument.mapOf(String.class, Object.class));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        boolean publish = config.get("publish") == null || (Boolean) config.get("publish");

        if (publish) {
            Set<ValidationMessage> assertions = jsonSchema.validate(content, InputFormat.JSON);

            if (!assertions.isEmpty()) {
                LOG.trace("Guide metadata {} does not validate the JSON Schema. Skipping guide.", configFile);
                return Optional.empty();
            }
        }

        Guide guide;
        try {
            guide = jsonMapper.readValue(content, Guide.class);
        } catch (IOException e) {
            LOG.trace("Error parsing guide metadata {}. Skipping guide.", configFile, e);
            return Optional.empty();
        }

        guide.setSlug(guidesDir.getName());
        guide.setAsciidoctor(publish ? guidesDir.getName() + ".adoc" : null);

        return Optional.of(guide);
    }
}
