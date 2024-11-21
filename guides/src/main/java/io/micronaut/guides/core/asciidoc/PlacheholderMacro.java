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

import java.util.Optional;

import static io.micronaut.guides.core.asciidoc.AsciidocMacro.MACRO_NAME_SEPARATOR;

/**
 * PlacheholderMacro is a record that represents a macro with a name and a target.
 *
 * @param name   the name of the macro
 * @param target the target of the macro
 */
public record PlacheholderMacro(String name, String target) {
    private static final String MACRO_BRACKET = "@";

    /**
     * Creates an Optional containing a PlacheholderMacro if the given string matches the macro format.
     *
     * @param name the name of the macro
     * @param str  the string to parse
     * @return an Optional containing a PlacheholderMacro if the string matches the macro format, otherwise an empty Optional
     */
    public static Optional<PlacheholderMacro> of(String name, String str) {
        if (str.startsWith(MACRO_BRACKET) && str.endsWith(name + MACRO_BRACKET)) {
            String target = str.replace(name + MACRO_BRACKET, "").replace(MACRO_BRACKET, "").replace(MACRO_NAME_SEPARATOR, "");
            return Optional.of(new PlacheholderMacro(name, target));
        }
        return Optional.empty();
    }
}
