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
import io.micronaut.starter.options.BuildTool;
import io.micronaut.starter.options.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.*;


/**
 * Represents a guide metadata.
 **/
@Serdeable
@JsonSchema
public class Guide {
    @JsonPropertyDescription("The guide's title")
    @NonNull
    @NotBlank
    private String title;

    @JsonPropertyDescription("The guide introduction")
    @NonNull
    @NotBlank
    private String intro;

    @JsonPropertyDescription("The guide's authors")
    @NotEmpty
    @NonNull
    private List<String> authors;

    @JsonPropertyDescription("The guide's categories")
    @NotEmpty
    @NonNull
    private List<String> categories;

    @JsonPropertyDescription("The guide publication date. It should follow the format YYYY-MM-DD")
    @NonNull
    @NotNull
    private LocalDate publicationDate;

    @JsonPropertyDescription("If the guide needs a minimum Java version, define it here")
    @Nullable
    private Integer minimumJavaVersion;

    @JsonPropertyDescription("If the guide needs a maximum Java version, define it here")
    @Nullable
    private Integer maximumJavaVersion;

    @JsonPropertyDescription("The acronym for the cloud service provider of the guide. For example, OCI for Oracle Cloud Infrastructure")
    @Nullable
    private Cloud cloud;

    @JsonPropertyDescription("Set it to true to skip running the tests for the Gradle applications for the guide")
    @JsonProperty(defaultValue = StringUtils.FALSE)
    @Nullable
    private Boolean skipGradleTests;

    @JsonPropertyDescription("Set it to true to skip running the tests for the Maven applications for the guide")
    @JsonProperty(defaultValue = StringUtils.FALSE)
    @Nullable
    private Boolean skipMavenTests;

    @JsonPropertyDescription("The guide asciidoc file. If not specified, the guide slug followed by the .adoc suffix is used")
    @Nullable
    private String asciidoctor;

    @JsonPropertyDescription("The guide supported languages")
    @Nullable
    private List<Language> languages;

    @JsonPropertyDescription("List of tags added to the guide. Features are added automatically as tags. No need to repeat them here")
    @Nullable
    private List<String> tags;

    @JsonPropertyDescription("By default the code in the guide is generated for Gradle and Maven. If a guide is specific only for a build tool, define it here")
    @Nullable
    private List<BuildTool> buildTools;

    @JsonPropertyDescription("The guide's test framework. By default Java and Kotlin applications are tested with JUnit5 and Groovy applications with Spock")
    @Nullable
    private TestFramework testFramework;

    @JsonPropertyDescription("List of additional files with a relative path to include in the generated zip file for the guide")
    @Nullable
    private List<String> zipIncludes;

    @JsonPropertyDescription("The guide's slug. If not specified, the guides folder is used")
    @Nullable
    private String slug;

    @JsonPropertyDescription("Whether the guide should be published, it defaults to true. You can set it to false for draft or base guides")
    @Nullable
    @JsonProperty(defaultValue = StringUtils.TRUE)
    private Boolean publish;

    @JsonPropertyDescription("Defaults to null; if set, indicates directory name of the base guide to copy before copying the current one")
    @Nullable
    private String base;

    @JsonPropertyDescription("The guide's environment variables")
    @Nullable
    private Map<String, String> env;

    @JsonPropertyDescription("Applications created for the guide")
    @NotEmpty
    @NonNull
    private List<App> apps;

    /**
     * Represents a guide metadata.
     *
     * @param title              The guide's title
     * @param intro              The guide introduction
     * @param authors            The guide's authors
     * @param categories         The guide's categories
     * @param publicationDate    The guide publication date. It should follow the format YYYY-MM-DD
     * @param minimumJavaVersion If the guide needs a minimum Java version, define it here
     * @param maximumJavaVersion If the guide needs a maximum Java version, define it here
     * @param cloud              The acronym for the cloud service provider of the guide. For example, OCI for Oracle Cloud Infrastructure
     * @param skipGradleTests    Set it to true to skip running the tests for the Gradle applications for the guide
     * @param skipMavenTests     Set it to true to skip running the tests for the Maven applications for the guide
     * @param asciidoctor        The guide asciidoc file. If not specified, the guide slug followed by the .adoc suffix is used
     * @param languages          The guide supported languages
     * @param tags               List of tags added to the guide. features are added automatically as tags. No need to repeat them here
     * @param buildTools         By default the code in the guide is generated for Gradle and Maven. If a guide is specific only for a build tool, define it here
     * @param testFramework      The guide's test framework. By default Java and Kotlin applications are tested with JUnit5 and Groovy applications with Spock
     * @param zipIncludes        List of additional files with a relative path to include in the generated zip file for the guide
     * @param slug               The guide's slug. If not specified, the guides folder is used
     * @param publish            Whether the guide should be published, it defaults to true. You can set it to false for draft or base guides
     * @param base               Defaults to null; if set, indicates directory name of the base guide to copy before copying the current one
     * @param env                The guide's environment variables
     * @param apps               Applications created for the guide
     */
    public Guide(@NonNull @NotBlank String title,
                 @NonNull @NotBlank String intro,
                 @NotEmpty @NonNull List<String> authors,
                 @NotEmpty @NonNull List<String> categories,
                 @NonNull @NotNull LocalDate publicationDate,
                 @Nullable Integer minimumJavaVersion,
                 @Nullable Integer maximumJavaVersion,
                 @Nullable Cloud cloud,
                 @Nullable Boolean skipGradleTests,
                 @Nullable Boolean skipMavenTests,
                 @Nullable String asciidoctor,
                 @Nullable List<Language> languages,
                 @Nullable List<String> tags,
                 @Nullable List<BuildTool> buildTools,
                 @Nullable TestFramework testFramework,
                 @Nullable List<String> zipIncludes,
                 @Nullable String slug,
                 @Nullable Boolean publish,
                 @Nullable String base,
                 @Nullable Map<String, String> env,
                 @NotEmpty @NonNull List<App> apps) {
        this.title = title;
        this.intro = intro;
        this.authors = authors;
        this.categories = categories;
        this.publicationDate = publish != null && publish ? publicationDate : null;
        this.minimumJavaVersion = minimumJavaVersion;
        this.maximumJavaVersion = maximumJavaVersion;
        this.cloud = cloud;
        this.skipGradleTests = skipGradleTests;
        this.skipMavenTests = skipMavenTests;
        this.asciidoctor = asciidoctor;
        this.languages = languages != null ? languages : List.of(Language.JAVA, Language.GROOVY, Language.KOTLIN);
        this.tags = tags != null ? tags : Collections.emptyList();
        this.buildTools = buildTools != null ? buildTools : List.of(BuildTool.GRADLE, BuildTool.MAVEN);
        this.zipIncludes = zipIncludes != null ? zipIncludes : new ArrayList<>();
        this.slug = slug;
        this.publish = publish;
        this.base = base;
        this.env = env != null ? env : new HashMap<>();
        this.apps = apps;
    }

    public @NonNull @NotBlank String title() {
        return title;
    }

    public void setTitle(@NonNull @NotBlank String title) {
        this.title = title;
    }

    public @NonNull @NotBlank String intro() {
        return intro;
    }

    public void setIntro(@NonNull @NotBlank String intro) {
        this.intro = intro;
    }

    public @NotEmpty @NonNull List<String> authors() {
        return authors;
    }

    public void setAuthors(@NotEmpty @NonNull List<String> authors) {
        this.authors = authors;
    }

    public @NotEmpty @NonNull List<String> categories() {
        return categories;
    }

    public void setCategories(@NotEmpty @NonNull List<String> categories) {
        this.categories = categories;
    }

    public @NonNull @NotNull LocalDate publicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(@NonNull @NotNull LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public @Nullable Integer minimumJavaVersion() {
        return minimumJavaVersion;
    }

    public void setMinimumJavaVersion(@Nullable Integer minimumJavaVersion) {
        this.minimumJavaVersion = minimumJavaVersion;
    }

    public @Nullable Integer maximumJavaVersion() {
        return maximumJavaVersion;
    }

    public void setMaximumJavaVersion(@Nullable Integer maximumJavaVersion) {
        this.maximumJavaVersion = maximumJavaVersion;
    }

    public @Nullable Cloud cloud() {
        return cloud;
    }

    public void setCloud(@Nullable Cloud cloud) {
        this.cloud = cloud;
    }

    public @Nullable Boolean skipGradleTests() {
        return skipGradleTests;
    }

    public void setSkipGradleTests(@Nullable Boolean skipGradleTests) {
        this.skipGradleTests = skipGradleTests;
    }

    public @Nullable Boolean skipMavenTests() {
        return skipMavenTests;
    }

    public void setSkipMavenTests(@Nullable Boolean skipMavenTests) {
        this.skipMavenTests = skipMavenTests;
    }

    public @Nullable String asciidoctor() {
        return asciidoctor;
    }

    public void setAsciidoctor(@Nullable String asciidoctor) {
        this.asciidoctor = asciidoctor;
    }

    public @Nullable List<Language> languages() {
        return languages;
    }

    public void setLanguages(@Nullable List<Language> languages) {
        this.languages = languages;
    }

    public @Nullable List<String> tags() {
        return tags;
    }

    public void setTags(@Nullable List<String> tags) {
        this.tags = tags;
    }

    public @Nullable List<BuildTool> buildTools() {
        return buildTools;
    }

    public void setBuildTools(@Nullable List<BuildTool> buildTools) {
        this.buildTools = buildTools;
    }

    public @Nullable TestFramework testFramework() {
        return testFramework;
    }

    public void setTestFramework(@Nullable TestFramework testFramework) {
        this.testFramework = testFramework;
    }

    public @Nullable List<String> zipIncludes() {
        return zipIncludes;
    }

    public void setZipIncludes(@Nullable List<String> zipIncludes) {
        this.zipIncludes = zipIncludes;
    }

    public @Nullable String slug() {
        return slug;
    }

    public void setSlug(@Nullable String slug) {
        this.slug = slug;
    }

    public @Nullable Boolean publish() {
        return publish;
    }

    public void setPublish(@Nullable Boolean publish) {
        this.publish = publish;
    }

    public @Nullable String base() {
        return base;
    }

    public void setBase(@Nullable String base) {
        this.base = base;
    }

    public @Nullable Map<String, String> env() {
        return env;
    }

    public void setEnv(@Nullable Map<String, String> env) {
        this.env = env;
    }

    public @NotEmpty @NonNull List<App> apps() {
        return apps;
    }

    public void setApps(@NotEmpty @NonNull List<App> apps) {
        this.apps = apps;
    }
}
