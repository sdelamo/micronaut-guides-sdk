package io.micronaut.guides.cli;

import io.micronaut.core.io.ResourceLoader;
import io.micronaut.guides.core.GuidesTemplatesConfiguration;
import io.micronaut.guides.core.TemplateLoaderUtils;

abstract class GradleMavenTabs {
    protected String gradleMavenTabsHtml;

    protected GradleMavenTabs(ResourceLoader resourceLoader, GuidesTemplatesConfiguration guidesTemplatesConfiguration) {
        this.gradleMavenTabsHtml = TemplateLoaderUtils.getClasspathTemplate(resourceLoader, guidesTemplatesConfiguration, "gradleMavenTabs.html");
    }
}
