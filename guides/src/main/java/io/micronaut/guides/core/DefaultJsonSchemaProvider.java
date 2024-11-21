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

import com.networknt.schema.*;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

/**
 * DefaultJsonSchemaProvider is a singleton class that implements the JsonSchemaProvider interface.
 * It provides methods to retrieve JSON schemas.
 */
@Singleton
public class DefaultJsonSchemaProvider implements JsonSchemaProvider {
    JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);

    /**
     * Returns the JSON schema for the guide metadata.
     *
     * @return the JSON schema
     */
    @Override
    @NonNull
    public JsonSchema getSchema() {
        SchemaValidatorsConfig.Builder builder = SchemaValidatorsConfig.builder();
        SchemaValidatorsConfig validatorsConfig = builder.build();
        return jsonSchemaFactory.getSchema(SchemaLocation.of("https://micronaut-projects.github.io/micronaut-guides-sdk/guide-metadata.schema.json"), validatorsConfig);
    }
}
