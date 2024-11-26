package io.micronaut.guides.cli;

import io.micronaut.core.io.ResourceLoader;
import io.micronaut.guides.core.Guide;
import io.micronaut.guides.core.GuidesOption;
import io.micronaut.guides.core.GuidesTemplatesConfiguration;
import io.micronaut.guides.core.MacroSubstitution;
import io.micronaut.guides.core.asciidoc.AsciidocMacro;
import jakarta.inject.Singleton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class GradleMavenCommandMacro extends GradleMavenTabs implements MacroSubstitution {
    public static final String MACRO = "gradle-maven-command";
    public static final String START = "<code class=\"language-bash hljs\" data-highlighted=\"yes\"  style=\"white-space: pre-line;\">";
    public static final String END = "</code>";
    public static final String GRADLE_REGEX = "gradle=([^,]+(?:,[^,=]+)*),maven=";
    public static final String MAVEN_REGEX = "maven=([^\\]]+)";

    GradleMavenCommandMacro(ResourceLoader resourceLoader, GuidesTemplatesConfiguration guidesTemplatesConfiguration) {
        super(resourceLoader, guidesTemplatesConfiguration);
    }

    @Override
    protected String gradle(AsciidocMacro asciidocMacro, Guide guide, GuidesOption option) {
        String gradleValue = extractValue(asciidocMacro.raw(), GRADLE_REGEX);
        return START + gradleValue + END;
    }

    @Override
    protected String maven(AsciidocMacro asciidocMacro, Guide guide, GuidesOption option) {
        String mavenValue = extractValue(asciidocMacro.raw(), MAVEN_REGEX);
        return START + mavenValue + END;
    }

    @Override
    protected String getMacro() {
        return MACRO;
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