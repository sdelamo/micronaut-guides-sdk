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

import jakarta.inject.Singleton;

@Singleton
public class CommonMacroSubstitution extends LineMacroSubstitution {

    @Override
    protected String getMacroName() {
        return "common";
    }

    @Override
    protected String getBaseDirectory() {
        return "{commonsDir}";
    }

    @Override
    protected String getPrefix() {
        return "common-";
    }
}
