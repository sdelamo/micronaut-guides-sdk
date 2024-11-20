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

import io.micronaut.guides.core.asciidoc.AsciidocConfiguration;
import jakarta.inject.Inject;

/**
 * Class for providing various configurations used in the application.
 */
public class ConfigurationsProvider {

    @Inject
    private GuidesConfiguration guidesConfiguration;

    @Inject
    private AsciidocConfiguration asciidocConfiguration;

    @Inject
    private RssFeedConfiguration rssFeedConfiguration;

    @Inject
    private JsonFeedConfiguration jsonFeedConfiguration;

    /**
     * Retrieves the guides configuration.
     *
     * @return the guides configuration
     */
    GuidesConfiguration getGuidesConfiguration() {
        return guidesConfiguration;
    }

    /**
     * Retrieves the Asciidoc configuration.
     *
     * @return the Asciidoc configuration
     */
    AsciidocConfiguration getAsciidocConfiguration() {
        return asciidocConfiguration;
    }

    /**
     * Retrieves the RSS feed configuration.
     *
     * @return the RSS feed configuration
     */
    RssFeedConfiguration getRssFeedConfiguration() {
        return rssFeedConfiguration;
    }

    /**
     * Retrieves the JSON feed configuration.
     *
     * @return the JSON feed configuration
     */
    JsonFeedConfiguration getJsonFeedConfiguration() {
        return jsonFeedConfiguration;
    }
}
