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

import io.micronaut.core.io.ResourceLoader;
import jakarta.inject.Singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * DefaultVersionLoader is a singleton class that implements the VersionLoader interface.
 * It loads the version information from a resource specified in the GuidesConfiguration.
 */
@Singleton
public class DefaultVersionLoader implements VersionLoader {
    private final String version;

    /**
     * Constructs a new DefaultVersionLoader with the specified guides configuration and resource loader.
     *
     * @param guidesConfiguration the guides configuration
     * @param resourceLoader      the resource loader
     */
    public DefaultVersionLoader(GuidesConfiguration guidesConfiguration, ResourceLoader resourceLoader) {
        Optional<InputStream> resourceAsStreamOptional = resourceLoader.getResourceAsStream(guidesConfiguration.getVersionPath());
        this.version = resourceAsStreamOptional.map(DefaultVersionLoader::loadVersion).orElse("0.0.0");
    }

    /**
     * Loads the version from the given input stream.
     *
     * @param inputStream the input stream
     * @return the version string
     */
    private static String loadVersion(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.readLine();
        } catch (IOException e) {
            throw new UnsupportedOperationException("Cannot load version number: ", e);
        }
    }

    /**
     * Returns the loaded version.
     *
     * @return the version string
     */
    @Override
    public String getVersion() {
        return version;
    }
}
