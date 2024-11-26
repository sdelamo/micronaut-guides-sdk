package io.micronaut.guides.cli;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.util.StringUtils;
import io.micronaut.guides.core.App;
import io.micronaut.guides.core.Cloud;
import io.micronaut.guides.core.Guide;
import io.micronaut.jsonschema.JsonSchema;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.starter.api.TestFramework;
import io.micronaut.starter.options.BuildTool;
import io.micronaut.starter.options.Language;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Serdeable
@JsonSchema
public class GdkGuide extends Guide {
    @JsonPropertyDescription("Set it to true to not generate code samples for the guide")
    @JsonProperty(defaultValue = StringUtils.FALSE)
    @Nullable
    private Boolean skipCodeSamples;

    @JsonPropertyDescription("Applications created for the guide")
    @NotEmpty
    @NonNull
    private List<GdkApp> apps;

    public @Nullable Boolean skipCodeSamples() {
        return skipCodeSamples;
    }

    public void setSkipCodeSamples(@Nullable Boolean skipCodeSamples) {
        this.skipCodeSamples = skipCodeSamples;
    }

    @Override
    public @NonNull List<GdkApp> getApps() {
        return apps;
    }

    @Override
    public void setApps(@NotEmpty @NonNull List<? extends App> apps) {
        this.apps = (List<GdkApp>) apps;
    }
}
