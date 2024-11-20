package io.micronaut.guides.core.html;

import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.guides.core.Guide;
import io.micronaut.guides.core.GuideGenerationUtils;
import io.micronaut.guides.core.GuidesOption;
import jakarta.inject.Singleton;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
            String href= guide.slug() + ".html";
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
