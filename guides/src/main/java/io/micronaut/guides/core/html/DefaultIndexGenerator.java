/*
 * Copyright 2017-2024 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.guides.core.html;

import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.guides.core.Guide;
import jakarta.inject.Singleton;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Default implementation of the {@link IndexGenerator} interface.
 * This class is responsible for generating an index for a list of guides.
 */
@Singleton
@Internal
class DefaultIndexGenerator implements IndexGenerator {

    @Override
    @NonNull
    public String renderIndex(@NonNull @NotNull List<Guide> guides) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html><head></head><body>");
        sb.append("<ul>");
        for (Guide guide : guides) {
            String href = guide.slug() + ".html";
            String title = guide.title();
            sb.append("<li>");
            sb.append("<a href=\"");
            sb.append(href);
            sb.append("\">");
            sb.append(title);
            sb.append("</a>");
            sb.append("</li>");
        }
        sb.append("</ul>");
        sb.append("</body></html>");
        return sb.toString();
    }
}
