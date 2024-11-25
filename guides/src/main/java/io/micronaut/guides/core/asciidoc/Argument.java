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
package io.micronaut.guides.core.asciidoc;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.util.StringUtils;

import java.util.Optional;

/**
 * Argument is a record that represents a key-value pair argument.
 *
 * @param key   the key of the argument
 * @param value the value of the argument
 */
public record Argument(String key, String value) {
    private static final String ARGUMENT_DELIMITATOR = ":";

    /**
     * Creates an Optional containing an Argument if the provided string is not empty and contains a key-value pair.
     *
     * @param str the string to parse
     * @return an Optional containing the Argument if the string is valid, otherwise an empty Optional
     */
    @NonNull
    public static Optional<Argument> of(@NonNull String str) {
        if (StringUtils.isEmpty(str)) {
            return Optional.empty();
        }

        String[] parts = str.split("=", 2);
        if (parts.length == 2 && !parts[0].isBlank() && !parts[1].isBlank()) {
            return Optional.of(new Argument(parts[0], parts[1]));
        }

        return Optional.empty();
    }

    /**
     * Returns a string representation of the Argument in the format ":key: value".
     *
     * @return the string representation of the Argument
     */
    @Override
    public String toString() {
        return ARGUMENT_DELIMITATOR + key + ARGUMENT_DELIMITATOR + " " + value;
    }
}
