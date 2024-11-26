package io.micronaut.guides.cli;

import io.micronaut.core.io.ResourceLoader;
import io.micronaut.guides.core.*;
import io.micronaut.guides.core.asciidoc.AsciidocMacro;
import jakarta.inject.Singleton;

import java.util.Optional;

import static io.micronaut.guides.core.MacroUtils.findMacroLines;

@Singleton
public class CreateAppMacro extends GradleMavenTabs implements MacroSubstitution {

    public static final String MACRO = "create-app";

    private final GuidesConfiguration guidesConfiguration;

    protected CreateAppMacro(ResourceLoader resourceLoader,
                             GuidesTemplatesConfiguration guidesTemplatesConfiguration,
                             GuidesConfiguration guidesConfiguration) {
        super(resourceLoader, guidesTemplatesConfiguration);
        this.guidesConfiguration = guidesConfiguration;
    }

    @Override
    public String substitute(String str, Guide guide, GuidesOption option) {
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
            String template = new StringBuilder()
                    .append("<code class=\"language-bash hljs\" data-highlighted=\"yes\"  style=\"white-space: pre-line;\">")
                    .append("gdk create-app ")
                    .append(guidesConfiguration.getPackageName())
                    .append(".")
                    .append(app.name())
                    .append(" \\\n")
                    .append(" --services=")
                    .append(String.join(",", app.services()))
                    .append(" \\\n")
                    .append(" --features=")
                    .append(String.join(",", app.features()))
                    .append((" \\\n"))
                    .append(" --lang=")
                    .append(option.getLanguage().name().toLowerCase())
                    .append(" \\\n")
                    .append(" --build={buildTool}\n")
                    .append("</code>")
                    .toString();


            String gradle = template.replace("{buildTool}", "gradle");
            String maven = template.replace("{buildTool}", "maven");
            str = str.replace(line, "++++\n" + gradleMavenTabsHtml.replace("{gradle}", gradle).replace("{maven}", maven) + "\n++++\n");
        }
        return str;
    }
}
