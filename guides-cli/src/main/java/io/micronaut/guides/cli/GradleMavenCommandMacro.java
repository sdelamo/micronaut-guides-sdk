package io.micronaut.guides.cli;

import io.micronaut.core.io.ResourceLoader;
import io.micronaut.guides.core.Guide;
import io.micronaut.guides.core.GuidesOption;
import io.micronaut.guides.core.GuidesTemplatesConfiguration;
import io.micronaut.guides.core.MacroSubstitution;
import jakarta.inject.Singleton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.micronaut.guides.core.MacroUtils.findMacroLines;

@Singleton
public class GradleMavenCommandMacro extends GradleMavenTabs implements MacroSubstitution {
    public static final String MACRO = "gradle-maven-command";

    GradleMavenCommandMacro(ResourceLoader resourceLoader, GuidesTemplatesConfiguration guidesTemplatesConfiguration) {
        super(resourceLoader, guidesTemplatesConfiguration);
    }

    @Override
    public String substitute(String str, Guide guide, GuidesOption option) {
        for (String line : findMacroLines(str, MACRO)) {
            String gradleRegex = "gradle=([^,]+(?:,[^,=]+)*),maven=";
            String mavenRegex = "maven=([^\\]]+)";
            String gradleValue = extractValue(str, gradleRegex);
            String mavenValue = extractValue(str, mavenRegex);

            String start = "<code class=\"language-bash hljs\" data-highlighted=\"yes\"  style=\"white-space: pre-line;\">";
            String end = "</code>";

            String gradle = start + gradleValue + end;
            String maven = start + mavenValue + end;

            str = str.replace(line, "++++\n" + gradleMavenTabsHtml.replace("{gradle}", gradle).replace("{maven}", maven) + "\n++++\n");
        }
        return str;
    }

    private static String extractValue(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
