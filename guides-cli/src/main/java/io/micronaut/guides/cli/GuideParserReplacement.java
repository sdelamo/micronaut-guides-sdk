package io.micronaut.guides.cli;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.guides.core.Guide;
import io.micronaut.guides.core.GuideMerger;
import io.micronaut.guides.core.GuideParser;
import io.micronaut.json.JsonMapper;
import jakarta.inject.Singleton;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
@Replaces(GuideParser.class)
public class GuideParserReplacement implements GuideParser {
    private static final Logger LOG = LoggerFactory.getLogger(io.micronaut.guides.core.DefaultGuideParser.class);

    private final JsonMapper jsonMapper;
    private final GuideMerger guideMerger;

    /**
     * Constructs a new DefaultGuideParser.
     *
     * @param jsonMapper the JSON mapper
     */
    public GuideParserReplacement(JsonMapper jsonMapper, GuideMerger guideMerger) {
        this.jsonMapper = jsonMapper;
        this.guideMerger = guideMerger;
    }

    @Override
    @NonNull
    public List<? extends Guide> parseGuidesMetadata(@NonNull @NotNull File guidesDir, @NonNull @NotNull String metadataConfigName) {
        List<GdkGuide> metadatas = new ArrayList<>();

        File[] dirs = guidesDir.listFiles(File::isDirectory);
        if (dirs == null) {
            return metadatas;
        }
        for (File dir : dirs) {
            parseGuideMetadata(dir, metadataConfigName).ifPresent(guide -> metadatas.add((GdkGuide) guide));
        }

        guideMerger.mergeGuides(metadatas);

        return metadatas;
    }

    @Override
    @NonNull
    public Optional<? extends Guide> parseGuideMetadata(@NonNull @NotNull File guidesDir, @NonNull @NotNull String metadataConfigName) {
        File configFile = new File(guidesDir, metadataConfigName);
        if (!configFile.exists()) {
            LOG.warn("metadata file not found for {}", guidesDir.getName());
            return Optional.empty();
        }

        String content;
        try {
            content = Files.readString(Paths.get(configFile.toString()));
        } catch (IOException e) {
            LOG.warn("metadata file not found for {}", guidesDir.getName());
            return Optional.empty();
        }


        GdkGuide guide;
        try {
            guide = jsonMapper.readValue(content, GdkGuide.class);
        } catch (IOException e) {
            LOG.trace("Error parsing guide metadata {}. Skipping guide.", configFile, e);
            return Optional.empty();
        }

        guide.setSlug(guidesDir.getName());
        guide.setAsciidoctor(guide.isPublish() ? guidesDir.getName() + ".adoc" : null);

        return Optional.of(guide);
    }
}
