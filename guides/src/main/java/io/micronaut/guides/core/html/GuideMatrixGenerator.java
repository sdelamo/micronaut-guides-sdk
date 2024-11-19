package io.micronaut.guides.core.html;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.guides.core.Guide;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GuideMatrixGenerator {

    @NonNull
    String renderIndex(@NonNull @NotNull @Valid Guide guide);
}
