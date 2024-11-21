package io.micronaut.guides.cli;

import io.micronaut.core.io.ResourceLoader;
import io.micronaut.guides.core.Guide;
import io.micronaut.guides.core.GuidesOption;
import io.micronaut.guides.core.GuidesTemplatesConfiguration;
import io.micronaut.guides.core.MacroSubstitution;
import jakarta.inject.Singleton;

import static io.micronaut.guides.core.MacroUtils.findMacroLines;

@Singleton
class DownloadMacro extends GradleMavenTabs implements MacroSubstitution {

    public static final String MACRO_DOWNLOAD = "download";

    DownloadMacro(ResourceLoader resourceLoader, GuidesTemplatesConfiguration guidesTemplatesConfiguration) {
        super(resourceLoader, guidesTemplatesConfiguration);
    }

    @Override
    public String substitute(String str, Guide guide, GuidesOption option) {
        for (String line : findMacroLines(str, MACRO_DOWNLOAD)) {

            String relativePathToGradleDownload = ""; //TODO
            String gradleTitle = ""; //TODO
            String gradle = "<a href=\"" + relativePathToGradleDownload +"\">" + gradleTitle+ " <img class=\"download-img-guides\" src=\"https://graal.cloud/gdk/resources/img/gdk_modules/download-archive.png\" alt=\"Download completed example\"></a>";

            String relativePathToMavenDownload = ""; //TODO
            String mavenTitle = ""; //TODO
            String maven = "<a href=\"" + relativePathToMavenDownload + " \"> "+ mavenTitle +" <img class=\"download-img-guides\" src=\"/gdk/resources/img/gdk_modules/download-archive.png\" alt=\"Download completed example\"></a>";
            str = str.replace(line, "++++\n" + gradleMavenTabsHtml.replace("{gradle}", gradle).replace("{maven}", maven) + "\n++++\n");
        }
        return str;
    }
}
