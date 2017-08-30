package de.arraying.zeus.token;

import de.arraying.zeus.backend.Patterns;

import java.util.regex.Pattern;

/**
 * Copyright 2017 Arraying
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class TokenEntry {

    private final Patterns type;
    private final Pattern pattern;

    /**
     * Creates a new token entry.
     * @param type The type of entry.
     */
    TokenEntry(Patterns type) {
        this.type = type;
        this.pattern = Pattern.compile(type.getStringPattern());
    }

    /**
     * Gets the token entry type.
     * @return The type.
     */
    public Patterns getType() {
        return type;
    }

    /**
     * Gets the token entry pattern.
     * @return The pattern.
     */
    Pattern getPattern() {
        return pattern;
    }

}
