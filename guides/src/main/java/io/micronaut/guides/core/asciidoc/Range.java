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

/**
 * Range is a record that represents a range with a starting point (from) and an ending point (to).
 *
 * @param from the starting point of the range
 * @param to   the ending point of the range
 */
public record Range(int from, int to) {

    /**
     * Checks if the range is valid.
     *
     * @return true if the range is valid, false otherwise
     */
    public boolean isValid() {
        if (from == to) {
            return false;
        }
        if (to == -1 && from == 0) {
            return false;
        }
        if (to != -1 && from > to) {
            return false;
        }
        return true;
    }
}
