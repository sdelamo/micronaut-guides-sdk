package io.micronaut.guides.core;

import io.micronaut.context.annotation.Primary;
import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
@Primary
@Internal
class CompositeMacroSubstitution implements MacroSubstitution {
    private final List<MacroSubstitution> substitutions;

    CompositeMacroSubstitution(List<MacroSubstitution> substitutions) {
        this.substitutions = substitutions;
    }

    @Override
    @NonNull
    public String substitute(@NonNull String str, @NonNull Guide guide, @NonNull GuidesOption option) {
        String result = str;
        for (MacroSubstitution substitution : substitutions) {
            result = substitution.substitute(result, guide, option);
        }
        return result;
    }
}
