package io.micronaut.guides.cli;

import io.micronaut.core.io.ResourceLoader;
import io.micronaut.guides.core.Guide;
import io.micronaut.guides.core.GuidesOption;
import io.micronaut.guides.core.GuidesTemplatesConfiguration;
import io.micronaut.guides.core.MacroSubstitution;

import static io.micronaut.guides.core.MacroUtils.findMacroLines;

public class CreateAppMacro extends GradleMavenTabs implements MacroSubstitution {

    public static final String MACRO = "create-app";

    protected CreateAppMacro(ResourceLoader resourceLoader, GuidesTemplatesConfiguration guidesTemplatesConfiguration) {
        super(resourceLoader, guidesTemplatesConfiguration);
    }

    @Override
    public String substitute(String str, Guide guide, GuidesOption option) {
        for (String line : findMacroLines(str, MACRO)) {

            String gradle = "";
            String maven = "";
            str = str.replace(line, "++++\n" + gradleMavenTabsHtml.replace("{gradle}", gradle).replace("{maven}", maven) + "\n++++\n");
        }
        return str;
    }
}
