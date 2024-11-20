package io.micronaut.guides.core.html;

import io.micronaut.guides.core.Guide;
import io.micronaut.guides.core.GuideGenerationUtils;
import io.micronaut.guides.core.GuidesOption;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Singleton
class DefaultGuideMatrixGenerator implements GuideMatrixGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultGuideMatrixGenerator.class);
    @Override
    public String renderIndex(Guide guide) {
        StringBuilder sb = new StringBuilder();
        List<GuidesOption> guideOptions = GuideGenerationUtils.guidesOptions(guide, LOG);
        sb.append("<!DOCTYPE html><html><head></head><body>");
        sb.append("<h1>");
        sb.append(guide.title());
        sb.append("</h1>");
        sb.append("<ul>");
        for (GuidesOption guideOption : guideOptions) {
            String href= guide.slug() + "-" + guideOption.getBuildTool() + "-" + guideOption.getLanguage() + ".html";
            String title = guideOption.getBuildTool() + " " + guideOption.getLanguage();
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
