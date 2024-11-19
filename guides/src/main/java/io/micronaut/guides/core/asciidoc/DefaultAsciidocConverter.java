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

import jakarta.inject.Singleton;
import org.asciidoctor.*;

import java.io.File;

/**
 * DefaultAsciidocConverter is a singleton class that implements the AsciidocConverter interface.
 * It provides methods to convert Asciidoc files to html using Asciidoctor.
 */
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
            .icons(asciidocConfiguration.getIcons())
            .imagesDir(asciidocConfiguration.getImagesdir())
            .noFooter(asciidocConfiguration.isNofooter());

        optionsBuilder = Options.builder()
            .docType(asciidocConfiguration.getDocType())
            .eruby(asciidocConfiguration.getRuby())
            .templateDirs(asciidocConfiguration.getTemplateDirs())
            .safe(SafeMode.UNSAFE)
            .baseDir(new File(asciidocConfiguration.getBaseDir()));

        asciidoctor = Asciidoctor.Factory.create();
    }

    /**
     * Converts the source Asciidoc file to the specified destination file.
     *
     * @param source      the source Asciidoc file
     * @param destination the destination file
     */
    @Override
    public void convert(File source, File destination) {
        asciidoctor.convertFile(source, optionsBuilder.attributes(attributesBuilder.build()).toFile(destination).build());
    }

    /**
     * Converts the source Asciidoc file to a string.
     *
     * @param source the source Asciidoc file
     * @return the converted content as a string
     */
    @Override
    public String convert(File source) {
        return asciidoctor.convertFile(source, optionsBuilder.attributes(attributesBuilder.build()).toFile(false).build());
    }
}
