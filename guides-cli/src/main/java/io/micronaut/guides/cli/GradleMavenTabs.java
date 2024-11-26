package io.micronaut.guides.cli;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.io.ResourceLoader;
import io.micronaut.guides.core.Guide;
import io.micronaut.guides.core.GuidesOption;
import io.micronaut.guides.core.GuidesTemplatesConfiguration;
import io.micronaut.guides.core.TemplateLoaderUtils;
import io.micronaut.guides.core.MacroSubstitution;
import io.micronaut.guides.core.asciidoc.AsciidocMacro;
import io.micronaut.guides.core.asciidoc.AsciidocUtils;

import java.util.Optional;

import static io.micronaut.guides.core.MacroUtils.findMacroLines;

abstract class GradleMavenTabs implements MacroSubstitution {
    protected String gradleMavenTabsHtml;

    protected GradleMavenTabs(ResourceLoader resourceLoader, GuidesTemplatesConfiguration guidesTemplatesConfiguration) {
        this.gradleMavenTabsHtml = TemplateLoaderUtils.getClasspathTemplate(resourceLoader, guidesTemplatesConfiguration, "gradleMavenTabs.html");
    }

    @NonNull
    protected abstract String getMacro();

    @NonNull
    protected abstract String gradle(AsciidocMacro asciidocMacro, Guide guide, GuidesOption option);

    @NonNull
    protected abstract String maven(AsciidocMacro asciidocMacro, Guide guide, GuidesOption option);

    @Override
    public String substitute(String str, Guide guide, GuidesOption option) {
        for (String line : findMacroLines(str, getMacro())) {
            Optional<AsciidocMacro> asciidocMacroOptional = AsciidocMacro.of(getMacro(), line);
            if (asciidocMacroOptional.isEmpty()) {
                continue;
            }
            str = str.replace(line, AsciidocUtils.passthroughBlock(gradleMavenTabsHtml.replace("{gradle}", gradle(asciidocMacroOptional.get(), guide, option))
                    .replace("{maven}", maven(asciidocMacroOptional.get(), guide, option))));
        }
        return str;
    }
}
