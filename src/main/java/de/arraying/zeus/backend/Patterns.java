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
     * The general token pattern.
     * A general token is any character that doesn't really fit in.
     */
    TOKEN("[=()]"),

    /**
     * The identifier pattern.
     * Identifiers are used for variable and method names.
     */
    IDENTIFIER("^[a-zA-Z_]{1}[a-zA-Z0-9_]+"),

    /**
     * The integer pattern.
     */
    TYPE_INT("^-?[0-9]+$"),

    /**
     * The boolean pattern.
     */
    TYPE_BOOL("^(true|false|t|f)$"),

    /**
     * The long pattern.
     */
    TYPE_LONG("^[Ll]-?[0-9]+$"),

    /**
     * The double pattern.
     */
    TYPE_DOUBLE("^-?[0-9]*\\.[0-9]+$"),

    /**
     * The string pattern.
     */
    TYPE_STRING("^\".*\"$"),

    /**
     * A single line comment pattern.
     * Prefixed with "TI" to indicate the tokenizer should ignore it.
     */
    TI_COMMENT_SINGLE("\\/\\/.*"),

    /**
     * A multi line comment pattern.
     * Prefixed with "TI" to indicate the tokenizer should ignore it.
     */
    TI_COMMENT_MULTIPLE("\\/\\*.*\\R*?.*\\*\\/");

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
