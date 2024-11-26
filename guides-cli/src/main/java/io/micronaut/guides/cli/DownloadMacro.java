package io.micronaut.guides.cli;

import io.micronaut.core.io.ResourceLoader;
import io.micronaut.guides.core.*;
import io.micronaut.guides.core.asciidoc.AsciidocConfiguration;
import io.micronaut.guides.core.asciidoc.AsciidocUtils;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;

import static io.micronaut.guides.core.MacroUtils.findMacroLines;

@Singleton
class DownloadMacro extends GradleMavenTabs implements MacroSubstitution {

    private final GuidesConfiguration guidesConfiguration;

    public static final String MACRO_DOWNLOAD = "download";
    public static final String BASE_DOWNLOAD_URL = "https://github.com/oracle/graal-dev-kit/releases/download/";

    DownloadMacro(ResourceLoader resourceLoader, GuidesTemplatesConfiguration guidesTemplatesConfiguration, GuidesConfiguration guidesConfiguration) {
        super(resourceLoader, guidesTemplatesConfiguration);
        this.guidesConfiguration = guidesConfiguration;
    }

    @Override
    public String substitute(String str, Guide guide, GuidesOption option) {
        for (String line : findMacroLines(str, MACRO_DOWNLOAD)) {
            String downloadTitle = capitalizeWords(guide.slug().replace("-", " "));

            String relativePathToGradleDownload = UriBuilder.of(BASE_DOWNLOAD_URL)
                    .path(guidesConfiguration.getVersion())
                    .path(guidesConfiguration.getVersion() + "_" + guide.slug() + "_gradle_java_example.zip")
                    .toString();
            String gradleTitle = "Gradle " + downloadTitle + " Example";
            String gradle = "<a href=\"" + relativePathToGradleDownload + "\">" + gradleTitle + " <img class=\"download-img-guides\" src=\"https://graal.cloud/gdk/resources/img/gdk_modules/download-archive.png\" alt=\"Download completed example\"></a>";

            String relativePathToMavenDownload = UriBuilder.of(BASE_DOWNLOAD_URL)
                    .path(guidesConfiguration.getVersion())
                    .path(guidesConfiguration.getVersion() + "_" + guide.slug() + "_gradle_java_example.zip")
                    .toString();
            String mavenTitle = "Maven " + downloadTitle + " Example";
            String maven = "<a href=\"" + relativePathToMavenDownload + " \"> " + mavenTitle + " <img class=\"download-img-guides\" src=\"https://graal.cloud/gdk/resources/img/gdk_modules/download-archive.png\" alt=\"Download completed example\"></a>";
            str = str.replace(line, AsciidocUtils.passthroughBlock(gradleMavenTabsHtml.replace("{gradle}", gradle)
                            .replace("{maven}", maven)));
        }
        return str;
    }

    private static String capitalizeWords(String sentence) {
        String[] words = sentence.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (word.isEmpty()) continue;
            String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            result.append(capitalizedWord).append(" ");
        }

        return result.toString().trim();
    }
}
