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

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.starter.api.TestFramework;
import io.micronaut.starter.options.BuildTool;
import io.micronaut.starter.options.Language;

/**
 * GuidesOption is a class that encapsulates the options for a guide, including the build tool,
 * programming language, and test framework.
 */
@Introspected
public class GuidesOption {

    @NonNull
    private BuildTool buildTool;

    @NonNull
    private Language language;

    @NonNull
    private TestFramework testFramework;

    /**
     * Constructs a new GuidesOption with the specified build tool, language, and test framework.
     *
     * @param buildTool     the build tool to be used
     * @param language      the programming language to be used
     * @param testFramework the test framework to be used
     */
    public GuidesOption(@NonNull BuildTool buildTool, @NonNull Language language, @NonNull TestFramework testFramework) {
        this.buildTool = buildTool;
        this.language = language;
        this.testFramework = testFramework;
    }

    /**
     * Returns the build tool.
     *
     * @return the build tool
     */
    @NonNull
    public BuildTool getBuildTool() {
        return buildTool;
    }

    /**
     * Sets the build tool.
     *
     * @param buildTool the build tool to be set
     */
    public void setBuildTool(@NonNull BuildTool buildTool) {
        this.buildTool = buildTool;
    }

    /**
     * Returns the programming language.
     *
     * @return the programming language
     */
    @NonNull
    public Language getLanguage() {
        return language;
    }

    /**
     * Sets the programming language.
     *
     * @param language the programming language to be set
     */
    public void setLanguage(@NonNull Language language) {
        this.language = language;
    }

    /**
     * Returns the test framework.
     *
     * @return the test framework
     */
    @NonNull
    public TestFramework getTestFramework() {
        return testFramework;
    }

    /**
     * Sets the test framework.
     *
     * @param testFramework the test framework to be set
     */
    public void setTestFramework(@NonNull TestFramework testFramework) {
        this.testFramework = testFramework;
    }
}
