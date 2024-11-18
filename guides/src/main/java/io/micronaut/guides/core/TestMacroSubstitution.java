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
import io.micronaut.guides.core.asciidoc.AsciidocMacro;
import io.micronaut.guides.core.asciidoc.Classpath;
import jakarta.inject.Singleton;

import static io.micronaut.starter.api.TestFramework.SPOCK;

@Singleton
public class TestMacroSubstitution extends SourceBlockMacroSubstitution {
    public static final String SUFFIX_SPEC = "Spec";
    private static final String MACRO_TEST = "test";
    private static final String SUFFIX_TEST = "Test";

    public TestMacroSubstitution(GuidesConfiguration guidesConfiguration, LicenseLoader licenseLoader) {
        super(licenseLoader, guidesConfiguration);
    }

    @Override
    public String getMacroName() {
        return MACRO_TEST;
    }

    @Override
    public Classpath getClasspath() {
        return Classpath.TEST;
    }

    @Override
    public FileType getFileType() {
        return FileType.CODE;
    }

    @Override
    public String condensedTarget(@NonNull AsciidocMacro asciidocMacro, GuidesOption option) {
        String target = asciidocMacro.target();
        if (target.endsWith(SUFFIX_TEST)) {
            target = target.substring(0, target.indexOf(SUFFIX_TEST));
            target += option.getTestFramework() == SPOCK ? SUFFIX_SPEC : SUFFIX_TEST;
        }
        return target;
    }
}
