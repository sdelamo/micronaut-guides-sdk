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
package io.micronaut.guides.core;

import jakarta.inject.Singleton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.micronaut.guides.core.MacroUtils.findMacroInstances;

@Singleton
public class GuideLinkMacroSubstitution implements MacroSubstitution {
    private static final Pattern GUIDE_LINK_REGEX = Pattern.compile("guideLink:(.*?)\\[(.*?)]");

    private static String processGuideLink(String line) {
        Matcher matcher = GUIDE_LINK_REGEX.matcher(line);
        if (matcher.find()) {
            String slug = matcher.group(1).trim();
            String text = matcher.group(2);
            return "link:" + slug + ".html[" + text + "]";
        }
        return line;
    }

    @Override
    public String substitute(String str, Guide guide, GuidesOption option) {
        for (String instance : findMacroInstances(str, GUIDE_LINK_REGEX)) {
            String res = processGuideLink(instance);
            str = str.replace(instance, res);
        }

        return str;
    }

}
