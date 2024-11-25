package io.micronaut.guides.cli;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.guides.core.App;
import io.micronaut.jsonschema.JsonSchema;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.starter.api.TestFramework;
import io.micronaut.starter.application.ApplicationType;

import java.util.ArrayList;
import java.util.List;

@Serdeable
@JsonSchema
public class GdkApp extends App {
    @JsonPropertyDescription("The GDK Starter services' name that the app requires")
    @Nullable
    private List<String> services;

    /**
     * Represents the app metadata.
     *
     * @param name              The app's name. For single application guides, the application needs to be named default
     * @param packageName       The app's package name. If you don't specify, the package name example.micronaut is used
     * @param applicationType   The app type.  If you don't specify, default is used
     * @param framework         The app's framework. Default is Micronaut but Spring Boot is also supported
     * @param features          The Micronaut Starter features' name that the app requires
     * @param services          The GDK Starter services' name that the app requires
     * @param invisibleFeatures The app's invisible features
     * @param kotlinFeatures    The app's Kotlin features
     * @param javaFeatures      The app's Java features
     * @param groovyFeatures    The app's Groovy features
     * @param testFramework     The app's test framework
     * @param excludeTest       The tests that should not be run
     * @param excludeSource     The source files that should not be included
     * @param validateLicense   To enable Spotless code check
     */
    public GdkApp(String name, String packageName, ApplicationType applicationType, String framework, List<String> features, List<String> services, List<String> invisibleFeatures, List<String> kotlinFeatures, List<String> javaFeatures, List<String> groovyFeatures, TestFramework testFramework, List<String> excludeTest, List<String> excludeSource, Boolean validateLicense) {
        super(name, packageName, applicationType, framework, features, invisibleFeatures, kotlinFeatures, javaFeatures, groovyFeatures, testFramework, excludeTest, excludeSource, validateLicense);
        this.services = services != null ? services : new ArrayList<>();
    }

    public @Nullable List<String> services() {
        return services;
    }

    public void setServices(@Nullable List<String> services) {
        this.services = services;
    }
}
