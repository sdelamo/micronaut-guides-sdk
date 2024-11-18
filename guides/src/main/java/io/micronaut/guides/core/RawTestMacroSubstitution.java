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
public class RawTestMacroSubstitution extends TestMacroSubstitution {
    private static final String MACRO_RAW_TEST = "rawTest";

    public RawTestMacroSubstitution(GuidesConfiguration guidesConfiguration, LicenseLoader licenseLoader) {
        super(guidesConfiguration, licenseLoader);
    }

    @Override
    public String getMacroName() {
        return MACRO_RAW_TEST;
    }

    @Override
    protected String getLanguage(GuidesOption option) {
        return option.getTestFramework().toTestFramework().getDefaultLanguage().toString();
    }

    @Override
    protected String getExtension(GuidesOption option) {
        return option.getTestFramework().toTestFramework().getDefaultLanguage().getExtension();
    }

}
