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

import io.micronaut.guides.core.asciidoc.Classpath;
import jakarta.inject.Singleton;

/**
 * Class that provides macro substitution functionality for test resource placeholders in guide templates.
 */
@Singleton
public class TestResourceMacroSubstitution extends SourceBlockMacroSubstitution {
    private static final String MACRO_TESTRESOURCE = "testResource";

    /**
     * Constructs a new TestResourceMacroSubstitution.
     *
     * @param guidesConfiguration the guides configuration
     * @param licenseLoader       the license loader
     */
    public TestResourceMacroSubstitution(GuidesConfiguration guidesConfiguration, LicenseLoader licenseLoader) {
        super(licenseLoader, guidesConfiguration);
    }

    /**
     * Returns the name of the macro.
     *
     * @return the macro name
     */
    @Override
    public String getMacroName() {
        return MACRO_TESTRESOURCE;
    }

    /**
     * Returns the classpath for the macro substitution.
     *
     * @return the classpath
     */
    @Override
    public Classpath getClasspath() {
        return Classpath.TEST;
    }

    /**
     * Returns the file type for the macro substitution.
     *
     * @return the file type
     */
    @Override
    public FileType getFileType() {
        return FileType.RESOURCE;
    }
}
