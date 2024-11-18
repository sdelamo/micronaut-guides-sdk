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

import io.micronaut.core.util.StringUtils;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class JdkLowerThanMacroExclusion extends MacroExclusion {
    private static final String MACRO_JDK_LOWER_THAN_EXCLUSION = "exclude-for-jdk-lower-than";

    private final GuidesConfiguration guidesConfiguration;

    public JdkLowerThanMacroExclusion(GuidesConfiguration guidesConfiguration) {
        this.guidesConfiguration = guidesConfiguration;
    }

    @Override
    protected String getMacroName() {
        return MACRO_JDK_LOWER_THAN_EXCLUSION;
    }

    @Override
    protected boolean shouldExclude(List<String> params, GuidesOption option, Guide guide) {
        if (StringUtils.isNotEmpty(params.get(0))) {
            Integer minJdk = Integer.valueOf(params.get(0));
            return (guide.minimumJavaVersion() != null ? guide.minimumJavaVersion() : guidesConfiguration.getDefaultMinJdk()) < minJdk;
        }
        return false;
    }
}
