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

import io.micronaut.context.ApplicationContext;
import io.micronaut.starter.build.dependencies.Coordinate;
import io.micronaut.starter.build.dependencies.PomDependencyVersionResolver;
import jakarta.inject.Singleton;

import java.util.Map;

/**
 * Interface for generating test scripts for guides.
 * This interface provides methods to check if an app or language supports native tests,
 * if an app uses the Micronaut framework, and to generate scripts for running tests.
 */
@Singleton
public class DefaultCoordinatesProvider implements CoordinatesProvider {

    /**
     * Retrieves the coordinates for dependencies by utilizing the PomDependencyVersionResolver.
     *
     * @return a map containing the coordinates of dependencies
     */
    @Override
    public Map<String, Coordinate> getCoordinates() {
        try (ApplicationContext context = ApplicationContext.run()) {
            PomDependencyVersionResolver pomDependencyVersionResolver = context.getBean(PomDependencyVersionResolver.class);
            return pomDependencyVersionResolver.getCoordinates();
        }
    }
}

