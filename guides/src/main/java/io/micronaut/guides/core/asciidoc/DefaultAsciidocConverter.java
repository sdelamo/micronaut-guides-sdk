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
import jakarta.inject.Singleton;
import jakarta.validation.constraints.NotBlank;
import org.asciidoctor.*;

import java.io.File;

@Singleton
public class DefaultAsciidocConverter implements AsciidocConverter {

    OptionsBuilder optionsBuilder;
    AttributesBuilder attributesBuilder;

    Asciidoctor asciidoctor;

    DefaultAsciidocConverter(AsciidocConfiguration asciidocConfiguration) {
        attributesBuilder = Attributes.builder()
                .attribute("sourcedir", asciidocConfiguration.getSourceDir())
                .attribute("commonsDir", asciidocConfiguration.getCommonsDir())
                .attribute("calloutsDir", asciidocConfiguration.getCalloutsDir())
                .attribute("guidesDir", asciidocConfiguration.getGuidesDir())
                .sourceHighlighter(asciidocConfiguration.getSourceHighlighter())
                .tableOfContents(asciidocConfiguration.getToc())
                .attribute("toclevels", asciidocConfiguration.getToclevels())
                .sectionNumbers(asciidocConfiguration.getSectnums())
                .attribute("idprefix", asciidocConfiguration.getIdprefix())
                .attribute("idseparator", asciidocConfiguration.getIdseparator())
                .icons(asciidocConfiguration.getIcons()).imagesDir(asciidocConfiguration.getImagesdir())
                .noFooter(asciidocConfiguration.isNofooter());

        optionsBuilder = Options.builder()
                .docType(asciidocConfiguration.getDocType())
                .eruby(asciidocConfiguration.getRuby())
                .safe(SafeMode.UNSAFE);

        if (StringUtils.isNotEmpty(asciidocConfiguration.getBaseDir())) {
            optionsBuilder.baseDir(new File(asciidocConfiguration.getBaseDir()));
        }

        asciidoctor = Asciidoctor.Factory.create();
    }

    @Override
    public String convert(@NonNull @NotBlank String asciidoc, @NonNull @NotBlank String sourceDir) {
        return asciidoctor.convert(asciidoc, optionsBuilder
                .toFile(false)
                .attributes(attributesBuilder.attribute("sourcedir", sourceDir).build())
                .build());
    }

    @Override
    public String convert(@NonNull File asciidoc, @NonNull @NotBlank String sourceDir) {
        return asciidoctor.convertFile(asciidoc, optionsBuilder
                .toFile(false)
                .attributes(attributesBuilder.attribute("sourcedir", sourceDir).build())
                .build());
    }

    @Override
    public void convert(@NonNull @NotBlank String asciidoc, @NonNull @NotBlank String sourceDir, @NonNull File destination) {
        asciidoctor.convert(asciidoc, optionsBuilder
                .toFile(destination)
                .attributes(attributesBuilder.attribute("sourcedir", sourceDir).build())
                .build());
    }

    @Override
    public void convert(@NonNull File asciidoc, @NonNull @NotBlank String sourceDir, @NonNull File destination) {
        asciidoctor.convertFile(asciidoc, optionsBuilder
                .toFile(destination)
                .attributes(attributesBuilder.attribute("sourcedir", sourceDir).build())
                .build());
    }
}
