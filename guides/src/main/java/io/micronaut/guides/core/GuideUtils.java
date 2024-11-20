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

import io.micronaut.starter.options.BuildTool;
import io.micronaut.starter.options.Language;

import java.util.*;
import java.util.stream.Collectors;

/**
 * GuideUtils is a utility class that provides various helper methods for handling guides.
 * It contains methods for retrieving tags, features, frameworks, and merging guide data.
 * This class cannot be instantiated.
 */
public final class GuideUtils {
    private static final String MICRONAUT_PREFIX = "micronaut-";
    private static final String VIEWS_PREFIX = "views-";
    private static final List<String> FEATURES_PREFIXES = List.of(MICRONAUT_PREFIX, VIEWS_PREFIX);

    private GuideUtils() { }

    /**
     * Retrieves a list of tags for a given guide.
     *
     * @param guide The guide from which to retrieve tags.
     * @return A list of tags associated with the guide.
     */
    public static List<String> getTags(Guide guide) {
        Set<String> tagsList = new HashSet<>();
        if (guide.tags() != null) {
            addAllSafe(tagsList, guide.tags());
        }
        for (App app : guide.apps()) {
            List<String> allFeatures = new ArrayList<>();
            addAllSafe(allFeatures, app.features());
            addAllSafe(allFeatures, app.javaFeatures());
            addAllSafe(allFeatures, app.kotlinFeatures());
            addAllSafe(allFeatures, app.groovyFeatures());
            for (String featureName : allFeatures) {
                String tagToAdd = featureName;
                for (String prefix : FEATURES_PREFIXES) {
                    if (tagToAdd.startsWith(prefix)) {
                        tagToAdd = tagToAdd.substring(prefix.length());
                    }
                }
                tagsList.add(tagToAdd);
            }
        }
        Set<String> categoriesAsTags = guide.categories().stream().map(String::toLowerCase).map(s -> s.replace(" ", "-")).collect(Collectors.toSet());
        tagsList.addAll(categoriesAsTags);
        return new ArrayList<>(tagsList);
    }

    /**
     * Retrieves a list of features for a given app and language.
     *
     * @param app      The app from which to retrieve features.
     * @param language The language for which to retrieve features.
     * @return A list of features associated with the app and language.
     */
    public static List<String> getAppFeatures(App app, Language language) {
        if (language == Language.JAVA) {
            return mergeLists(app.features(), getAppInvisibleFeatures(app), app.javaFeatures());
        }
        if (language == Language.KOTLIN) {
            return mergeLists(app.features(), getAppInvisibleFeatures(app), app.kotlinFeatures());
        }
        if (language == Language.GROOVY) {
            return mergeLists(app.features(), getAppInvisibleFeatures(app), app.groovyFeatures());
        }
        return mergeLists(app.features(), getAppInvisibleFeatures(app));
    }

    /**
     * Retrieves a list of invisible features for a given app.
     *
     * @param app The app from which to retrieve invisible features.
     * @return A list of invisible features associated with the app.
     */
    public static List<String> getAppInvisibleFeatures(App app) {
        if (app.validateLicense()) {
            List<String> result = new ArrayList<>();
            addAllSafe(result, app.invisibleFeatures());
            return result;
        }
        return app.invisibleFeatures();
    }

    /**
     * Retrieves a list of visible features for a given app and language.
     *
     * @param app      The app from which to retrieve visible features.
     * @param language The language for which to retrieve visible features.
     * @return A list of visible features associated with the app and language.
     */
    public static List<String> getAppVisibleFeatures(App app, Language language) {
        if (language == Language.JAVA) {
            return mergeLists(app.features(), app.javaFeatures());
        }
        if (language == Language.KOTLIN) {
            return mergeLists(app.features(), app.kotlinFeatures());
        }
        if (language == Language.GROOVY) {
            return mergeLists(app.features(), app.groovyFeatures());
        }
        return app.features();
    }

    /**
     * Determines if a guide should skip tests based on the build tool.
     *
     * @param guide     The guide to check.
     * @param buildTool The build tool to check against.
     * @return True if the guide should skip tests, false otherwise.
     */
    public static boolean shouldSkip(Guide guide, BuildTool buildTool) {
        if (BuildTool.valuesGradle().contains(buildTool)) {
            return guide.skipGradleTests();
        }
        if (buildTool == BuildTool.MAVEN) {
            return guide.skipMavenTests();
        }
        return false;
    }

    /**
     * Retrieves a set of frameworks used in a given guide.
     *
     * @param guide The guide from which to retrieve frameworks.
     * @return A set of frameworks associated with the guide.
     */
    public static Set<String> getFrameworks(Guide guide) {
        return guide.apps().stream().map(App::framework).collect(Collectors.toSet());
    }

    /**
     * Merges two guides into one.
     *
     * @param base  The base guide.
     * @param guide The guide to merge with the base.
     * @return A new guide that is the result of merging the base and the guide.
     */
    public static Guide merge(Guide base, Guide guide) {
        return new Guide(
                guide.title() == null ? base.title() : guide.title(),
                guide.intro() == null ? base.intro() : guide.intro(),
                mergeLists(base.authors(), guide.authors()),
                guide.categories() == null ? base.categories() : guide.categories(),
                guide.publicationDate(),
                guide.minimumJavaVersion() == null ? base.minimumJavaVersion() : guide.minimumJavaVersion(),
                guide.maximumJavaVersion() == null ? base.maximumJavaVersion() : guide.maximumJavaVersion(),
                guide.cloud() == null ? base.cloud() : guide.cloud(),
                base.skipGradleTests() || guide.skipGradleTests(),
                base.skipMavenTests() || guide.skipMavenTests(),
                guide.asciidoctor(),
                guide.languages() == null ? base.languages() : guide.languages(),
                mergeLists(GuideUtils.getTags(base), GuideUtils.getTags(guide)),
                guide.buildTools() == null ? base.buildTools() : guide.buildTools(),
                guide.testFramework() == null ? base.testFramework() : guide.testFramework(),
                guide.zipIncludes(),
                guide.slug(),
                guide.publish(),
                guide.base(),
                guide.env() == null ? base.env() : guide.env(),
                mergeApps(base.apps(), guide.apps())
        );
    }

    private static List<App> mergeApps(List<App> base, List<App> guide) {
        Map<String, App> baseApps = base.stream()
                .collect(Collectors.toMap(App::name, app -> app));

        Map<String, App> guideApps = guide.stream()
                .collect(Collectors.toMap(App::name, app -> app));

        Set<String> baseOnly = new HashSet<>(baseApps.keySet());
        baseOnly.removeAll(guideApps.keySet());

        Set<String> guideOnly = new HashSet<>(guideApps.keySet());
        guideOnly.removeAll(baseApps.keySet());

        Set<String> inBoth = new HashSet<>(baseApps.keySet());
        inBoth.retainAll(guideApps.keySet());

        List<App> merged = new ArrayList<>();
        merged.addAll(baseOnly.stream()
                .map(baseApps::get)
                .toList());
        merged.addAll(guideOnly.stream()
                .map(guideApps::get)
                .toList());

        for (String name : inBoth) {
            App baseApp = baseApps.get(name);
            App guideApp = guideApps.get(name);
            App mergedApp = new App(
                    guideApp.name(),
                    guideApp.packageName(),
                    guideApp.applicationType(),
                    guideApp.framework(),
                    mergeLists(guideApp.features(), baseApp.features()),
                    mergeLists(guideApp.invisibleFeatures(), baseApp.invisibleFeatures()),
                    mergeLists(guideApp.javaFeatures(), baseApp.javaFeatures()),
                    mergeLists(guideApp.kotlinFeatures(), baseApp.kotlinFeatures()),
                    mergeLists(guideApp.groovyFeatures(), baseApp.groovyFeatures()),
                    guideApp.testFramework(),
                    guideApp.excludeTest(),
                    guideApp.excludeSource(),
                    baseApp.validateLicense()
            );
            merged.add(mergedApp);
        }

        return merged;
    }

    /**
     * Merges multiple collections into one list.
     *
     * @param lists An array of Collection objects to be merged.
     * @return A single List containing all elements from the provided Collections, excluding any null values.
     */
    @SafeVarargs
    private static List<String> mergeLists(Collection<String>... lists) {
        List<String> merged = new ArrayList<>();
        for (Collection<String> list : lists) {
            if (list != null) {
                merged.addAll(list);
            }
        }
        return merged;
    }

    /**
     * Adds all elements from the source collection to the target collection safely.
     *
     * @param target The collection where elements will be added.
     * @param src    The collection whose elements are to be added to the target.
     * @throws NullPointerException If the target collection is null.
     */
    private static void addAllSafe(Collection<String> target, Collection<String> src) {
        if (target == null) {
            throw new NullPointerException("Target list cannot be null");
        }

        if (src != null) {
            target.addAll(src);
        }
    }

    /**
     * Merges a list of guide metadata.
     *
     * @param metadatas The list of guide metadata to be merged.
     */
    static void mergeMetadataList(List<Guide> metadatas) {
        Map<String, Guide> metadatasByDirectory = new TreeMap<>();
        for (Guide metadata : metadatas) {
            metadatasByDirectory.put(metadata.slug(), metadata);
        }

        mergeMetadataMap(metadatasByDirectory);

        metadatas.clear();
        metadatas.addAll(metadatasByDirectory.values());
    }

    private static void mergeMetadataMap(Map<String, Guide> metadatasByDirectory) {
        List<String> dirs = new ArrayList<>(metadatasByDirectory.keySet());

        for (String dir : dirs) {
            Guide metadata = metadatasByDirectory.get(dir);
            if (metadata.base() != null) {
                Guide base = metadatasByDirectory.get(metadata.base());
                Guide merged = GuideUtils.merge(base, metadata);
                metadatasByDirectory.put(dir, merged);
            }
        }
    }
}
