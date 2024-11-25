package io.micronaut.guides.cli;

import io.micronaut.guides.core.*;
import io.micronaut.guides.core.asciidoc.AsciidocMacro;
import io.micronaut.starter.application.Project;
import io.micronaut.starter.application.generator.GeneratorContext;
import io.micronaut.starter.application.generator.ProjectGenerator;
import io.micronaut.starter.io.ConsoleOutput;
import io.micronaut.starter.options.JdkVersion;
import io.micronaut.starter.options.Options;
import io.micronaut.starter.util.NameUtils;
import jakarta.inject.Singleton;

import java.util.Collections;
import java.util.Optional;

import static io.micronaut.guides.core.MacroUtils.findMacroLines;
import static io.micronaut.starter.options.JdkVersion.JDK_8;

@Singleton
public class FeaturesListingMacroSubstitution implements MacroSubstitution {

    public static final String MACRO = "features-listing";

    private final ProjectGenerator projectGenerator;
    private final GuidesConfiguration guidesConfiguration;

    FeaturesListingMacroSubstitution(ProjectGenerator projectGenerator, GuidesConfiguration guidesConfiguration) {
        this.projectGenerator = projectGenerator;
        this.guidesConfiguration = guidesConfiguration;
    }

    @Override
    public String substitute(String str, Guide guide, GuidesOption option) {
        JdkVersion javaVersion = GuideGenerationUtils.resolveJdkVersion(guidesConfiguration, guide);
        Project project = NameUtils.parse("com.example");
        for (String line : findMacroLines(str, MACRO)) {
            Optional<AsciidocMacro> asciidocMacroOptional = AsciidocMacro.of(MACRO, line);
            if (asciidocMacroOptional.isEmpty()) {
                continue;
            }
            String target = asciidocMacroOptional.get().target();
            GdkApp app = (GdkApp) guide.apps().
                    stream()
                    .filter(a -> a.name().equals(target))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("App not found: " + target));
            GeneratorContext generatorContext = projectGenerator.createGeneratorContext(
                    app.applicationType(),
                    project,
                    new Options(
                            option.getLanguage(),
                            option.getTestFramework().toTestFramework(),
                            option.getBuildTool(),
                            javaVersion != null ? javaVersion : JDK_8).withFramework(app.framework()),
                    null,
                    app.features() != null ? app.features() : Collections.emptyList(),
                    ConsoleOutput.NOOP
            );
            str = str.replace(line, """
                    [source,bash]
                    ----
                    features: """ +
                    generatorContext.getFeatures().toString() + "\n----\n");
        }
        return str;
    }
}
