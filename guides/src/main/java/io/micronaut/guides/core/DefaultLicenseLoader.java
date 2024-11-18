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
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class DefaultLicenseLoader implements LicenseLoader {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultLicenseLoader.class);
    private final int numberOfLines;
    private final String licenseHeaderText;
    private final Map<Integer, String> headerByYear = new ConcurrentHashMap<>();

    public DefaultLicenseLoader(GuidesConfiguration guidesConfiguration,
                                ResourceLoader resourceLoader) {
        Optional<InputStream> resourceAsStreamOptional = resourceLoader.getResourceAsStream(guidesConfiguration.getLicensePath());
        this.licenseHeaderText = resourceAsStreamOptional.map(this::readLicenseHeader).orElse("");
        this.numberOfLines = StringUtils.isEmpty(licenseHeaderText)
                ? 0 :
                (int) licenseHeaderText.lines().count() + 1;
    }

    private String readLicenseHeader(InputStream inputStream) {
        return headerByYear.computeIfAbsent(LocalDate.now().getYear(), year -> {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                reader.lines().forEach(line -> sb.append(line).append("\n"));
            } catch (IOException e) {
                LOG.error("", e);
            }
            return sb.toString().replace("$YEAR", String.valueOf(year));
        });
    }

    @Override
    public int getNumberOfLines() {
        return this.numberOfLines;
    }

    @Override
    public String getLicenseHeaderText() {
        return this.licenseHeaderText;
    }
}
