package de.arraying.zeus.backend;

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
public enum Patterns {

    /**
     * The identifier pattern.
     * Identifiers are used for variable and method names.
     */
    IDENTIFIER("^[a-zA-Z_]{1}[a-zA-Z0-9_]+"),
    TYPE_INT("^-?[0-9]+$"),
    TYPE_BOOL("^(true|false|t|f)$"),
    TYPE_LONG("^[Ll]-?[0-9]+$"),
    TYPE_DOUBLE("^-?[0-9]*\\.[0-9]+$"),
    TYPE_STRING("^\".*\"$");

    private final String stringPattern;

    /**
     * Sets the pattern.
     * @param stringPattern A regular expression as a string.
     */
    Patterns(String stringPattern) {
        this.stringPattern = stringPattern;
    }

    /**
     * Gets the string pattern.
     * @return A string version of the regular expression.
     */
    public String getStringPattern() {
        return getStringPattern(false);
    }

    /**
     * Gets the string pattern.
     * @param startsWith Whether or not to remove the leading starts with expression ("^").
     * @return A string version of the regular expression.
     */
    public String getStringPattern(boolean startsWith) {
        if(startsWith
                && stringPattern.charAt(0) == '^') {
            return stringPattern.substring(1);
        }
        return stringPattern;
    }

}
