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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.util.StringUtils;
import io.micronaut.jsonschema.JsonSchema;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.starter.api.TestFramework;
import io.micronaut.starter.application.ApplicationType;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an application metadata.
 */
@Serdeable
@JsonSchema
public class App {
    public static final String SPOTLESS = "spotless";

    @JsonPropertyDescription("The app's name. For single application guides, the application needs to be named default")
    @NonNull
    @NotBlank
    private String name;

    @JsonPropertyDescription("The app's package name. If you don't specify, the package name example.micronaut is used")
    @JsonProperty(defaultValue = "example.micronaut")
    @Nullable
    private String packageName;

    @JsonPropertyDescription("The app type. If you don't specify, default is used")
    @JsonProperty(defaultValue = "DEFAULT")
    @Nullable
    private ApplicationType applicationType;

    @JsonPropertyDescription("The app's framework. Default is Micronaut but Spring Boot is also supported")
    @JsonProperty(defaultValue = "Micronaut")
    @Nullable
    private String framework;

    @JsonPropertyDescription("The Micronaut Starter features' name that the app requires")
    @Nullable
    private List<String> features;

    @JsonPropertyDescription("The app's invisible features")
    @Nullable
    private List<String> invisibleFeatures;

    @JsonPropertyDescription("The app's Kotlin features")
    @Nullable
    private List<String> kotlinFeatures;

    @JsonPropertyDescription("The app's Java features")
    @Nullable
    private List<String> javaFeatures;

    @JsonPropertyDescription("The app's Groovy features")
    @Nullable
    private List<String> groovyFeatures;

    @JsonPropertyDescription("The app's test framework")
    @Nullable
    private TestFramework testFramework;

    @JsonPropertyDescription("The tests that should not be run")
    @Nullable
    private List<String> excludeTest;

    @JsonPropertyDescription("The source files that should not be included")
    @Nullable
    private List<String> excludeSource;

    @JsonPropertyDescription("To enable Spotless code check")
    @JsonProperty(defaultValue = StringUtils.TRUE)
    @Nullable
    private Boolean validateLicense;

    /**
     * Represents the app metadata.
     *
     * @param name              The app's name. For single application guides, the application needs to be named default
     * @param packageName       The app's package name. If you don't specify, the package name example.micronaut is used
     * @param applicationType   The app type.  If you don't specify, default is used
     * @param framework         The app's framework. Default is Micronaut but Spring Boot is also supported
     * @param features          The Micronaut Starter features' name that the app requires
     * @param invisibleFeatures The app's invisible features
     * @param kotlinFeatures    The app's Kotlin features
     * @param javaFeatures      The app's Java features
     * @param groovyFeatures    The app's Groovy features
     * @param testFramework     The app's test framework
     * @param excludeTest       The tests that should not be run
     * @param excludeSource     The source files that should not be included
     * @param validateLicense   To enable Spotless code check
     */
    public App(@NonNull @NotBlank String name,
               @Nullable String packageName,
               @Nullable ApplicationType applicationType,
               @Nullable String framework,
               @Nullable List<String> features,
               @Nullable List<String> invisibleFeatures,
               @Nullable List<String> kotlinFeatures,
               @Nullable List<String> javaFeatures,
               @Nullable List<String> groovyFeatures,
               @Nullable TestFramework testFramework,
               @Nullable List<String> excludeTest,
               @Nullable List<String> excludeSource,
               @Nullable Boolean validateLicense) {
        this.name = name;
        this.packageName = packageName;
        this.applicationType = applicationType;
        this.framework = framework;
        this.features = features != null ? features : new ArrayList<>();
        this.invisibleFeatures = invisibleFeatures != null ? invisibleFeatures : new ArrayList<>();
        this.kotlinFeatures = kotlinFeatures != null ? kotlinFeatures : new ArrayList<>();
        this.javaFeatures = javaFeatures != null ? javaFeatures : new ArrayList<>();
        this.groovyFeatures = groovyFeatures != null ? groovyFeatures : new ArrayList<>();
        this.testFramework = testFramework;
        this.excludeTest = excludeTest;
        this.excludeSource = excludeSource;
        this.validateLicense = validateLicense != null ? validateLicense && hasSpotless(this.features, this.invisibleFeatures, this.kotlinFeatures, this.javaFeatures, this.groovyFeatures) : null;
    }

    public @NonNull @NotBlank String name() {
        return name;
    }

    public void setName(@NonNull @NotBlank String name) {
        this.name = name;
    }

    public @Nullable String packageName() {
        return packageName;
    }

    public void setPackageName(@Nullable String packageName) {
        this.packageName = packageName;
    }

    public @Nullable ApplicationType applicationType() {
        return applicationType;
    }

    public void setApplicationType(@Nullable ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public @Nullable String framework() {
        return framework;
    }

    public void setFramework(@Nullable String framework) {
        this.framework = framework;
    }

    public @Nullable List<String> features() {
        return features;
    }

    public void setFeatures(@Nullable List<String> features) {
        this.features = features;
    }

    public @Nullable List<String> invisibleFeatures() {
        return invisibleFeatures;
    }

    public void setInvisibleFeatures(@Nullable List<String> invisibleFeatures) {
        this.invisibleFeatures = invisibleFeatures;
    }

    public @Nullable List<String> kotlinFeatures() {
        return kotlinFeatures;
    }

    public void setKotlinFeatures(@Nullable List<String> kotlinFeatures) {
        this.kotlinFeatures = kotlinFeatures;
    }

    public @Nullable List<String> javaFeatures() {
        return javaFeatures;
    }

    public void setJavaFeatures(@Nullable List<String> javaFeatures) {
        this.javaFeatures = javaFeatures;
    }

    public @Nullable List<String> groovyFeatures() {
        return groovyFeatures;
    }

    public void setGroovyFeatures(@Nullable List<String> groovyFeatures) {
        this.groovyFeatures = groovyFeatures;
    }

    public @Nullable TestFramework testFramework() {
        return testFramework;
    }

    public void setTestFramework(@Nullable TestFramework testFramework) {
        this.testFramework = testFramework;
    }

    public @Nullable List<String> excludeTest() {
        return excludeTest;
    }

    public void setExcludeTest(@Nullable List<String> excludeTest) {
        this.excludeTest = excludeTest;
    }

    public @Nullable List<String> excludeSource() {
        return excludeSource;
    }

    public void setExcludeSource(@Nullable List<String> excludeSource) {
        this.excludeSource = excludeSource;
    }

    public @Nullable Boolean validateLicense() {
        return validateLicense;
    }

    public void setValidateLicense(@Nullable Boolean validateLicense) {
        this.validateLicense = validateLicense;
    }

    @SafeVarargs
    private boolean hasSpotless(List<String>... featureLists) {
        for (List<String> features : featureLists) {
            if (features != null && features.contains(SPOTLESS)) {
                return true;
            }
        }
        return false;
    }
}


