package io.micronaut.guides.core;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.starter.application.ApplicationType;
import io.micronaut.starter.application.generator.GeneratorContext;
import io.micronaut.starter.build.dependencies.Dependency;
import io.micronaut.starter.feature.Feature;
import jakarta.inject.Singleton;

@Singleton
class SpringBootStarterWeb implements Feature {
    public static final String GROUP_ID_ORG_SPRINGFRAMEWORK_BOOT = "org.springframework.boot";
    private static final String ARTIFACT_ID_SPRING_BOOT_STARTER_WEB = "spring-boot-starter-web";
    private static final String ARTIFACT_ID_SPRING_BOOT_STARTER_TEST = "spring-boot-starter-test";

    public static final String NAME = "spring-boot-starter-web";
    public static final Dependency.Builder DEPENDENCY_SPRING_BOOT_STARTER_WEB = Dependency.builder()
            .groupId(GROUP_ID_ORG_SPRINGFRAMEWORK_BOOT)
            .artifactId(ARTIFACT_ID_SPRING_BOOT_STARTER_WEB)
            .compile();
    public static final Dependency.Builder DEPENDENCY_SPRINGBOOT_STARTER_TEST = Dependency.builder()
            .groupId(GROUP_ID_ORG_SPRINGFRAMEWORK_BOOT)
            .artifactId(ARTIFACT_ID_SPRING_BOOT_STARTER_TEST)
            .test();

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    @Override
    public String getTitle() {
        return "Spring Web";
    }

    @Override
    @NonNull
    public String getDescription() {
        return "Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.";
    }



    @Override
    public void apply(GeneratorContext generatorContext) {
        generatorContext.addDependency(DEPENDENCY_SPRING_BOOT_STARTER_WEB);
        generatorContext.addDependency(DEPENDENCY_SPRINGBOOT_STARTER_TEST);
    }

    @Override
    public boolean supports(ApplicationType applicationType) {
        return true;
    }
}
