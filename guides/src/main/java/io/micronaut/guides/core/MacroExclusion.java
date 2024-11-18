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

import java.util.List;

abstract class MacroExclusion implements MacroSubstitution {
    private static final String LINE_BREAK = "\n";

    protected abstract String getMacroName();

    protected abstract boolean shouldExclude(List<String> params, GuidesOption option, Guide guide);

    @Override
    public String substitute(String str, Guide guide, GuidesOption option) {
        for (List<String> group : MacroUtils.findMacroGroupsNested(str, getMacroName())) {
            List<String> params = MacroUtils.extractMacroGroupParameters(group.get(0), getMacroName());
            if (shouldExclude(params, option, guide)) {
                str = str.replace(String.join(LINE_BREAK, group) + LINE_BREAK, "");
            } else {
                str = str.replace(String.join(LINE_BREAK, group), String.join(LINE_BREAK, group.subList(1, group.size() - 1)));
            }
        }
        return str;
    }
}
