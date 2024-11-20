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
import io.micronaut.starter.build.dependencies.Coordinate;
import io.micronaut.starter.util.VersionInfo;
import jakarta.inject.Singleton;

import java.util.Map;

import static io.micronaut.starter.api.TestFramework.SPOCK;

@Singleton
public class PlaceholderMacroSubstitution implements MacroSubstitution {

    private final GuidesConfiguration guidesConfiguration;
    private final VersionLoader versionLoader;
    private final CoordinatesProvider coordinatesProvider;

    public PlaceholderMacroSubstitution(GuidesConfiguration guidesConfiguration, VersionLoader versionLoader, CoordinatesProvider coordinatesProvider) {
        this.guidesConfiguration = guidesConfiguration;
        this.versionLoader = versionLoader;
        this.coordinatesProvider = coordinatesProvider;
    }

    @Override
    public String substitute(String str, Guide guide, GuidesOption option) {

        str = str.replace("{githubSlug}", guide.slug());
        str = str.replace("@language@", StringUtils.capitalize(option.getLanguage().toString()));
        str = str.replace("@guideTitle@", guide.title());
        str = str.replace("@guideIntro@", guide.intro());
        str = str.replace("@micronaut@", String.valueOf(versionLoader.getVersion()));
        str = str.replace("@lang@", option.getLanguage().toString());
        str = str.replace("@build@", option.getBuildTool().toString());
        str = str.replace("@testFramework@", option.getTestFramework().toString());
        str = str.replace("@authors@", String.join(", ", guide.authors()));
        str = str.replace("@languageextension@", option.getLanguage().getExtension());
        str = str.replace("@testsuffix@", option.getTestFramework() == SPOCK ? "Spec" : "Test");
        str = str.replace("@sourceDir@", MacroUtils.getSourceDir(guide.slug(), option));
        str = str.replace("@minJdk@", String.valueOf(guide.minimumJavaVersion() != null ? guide.minimumJavaVersion() : guidesConfiguration.getDefaultMinJdk()));
        str = str.replace("@api@", guidesConfiguration.getApiUrl());

        for (Map.Entry<String, Coordinate> entry : coordinatesProvider.getCoordinates().entrySet()) {
            if (StringUtils.isNotEmpty(entry.getValue().getVersion())) {
                str = str.replace("@" + entry.getKey() + "Version@", entry.getValue().getVersion());
            }
        }

        str = str.replace("@micronautVersion@", VersionInfo.getMicronautVersion());

        return str;
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
