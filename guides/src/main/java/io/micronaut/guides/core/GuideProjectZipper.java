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

import io.micronaut.core.annotation.NonNull;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;

/**
 * Interface for zipping a directory into a single output file.
 */
public interface GuideProjectZipper {

    /**
     * Zips the contents of the specified source directory into the specified output file.
     *
     * @param sourceDir  the path to the source directory to be zipped
     * @param outputFile the path to the output file where the zipped content will be written
     * @throws IOException if an I/O error occurs during zipping
     */
    void zipDirectory(@NonNull @NotNull String sourceDir, @NonNull @NotNull String outputFile) throws IOException;
}
