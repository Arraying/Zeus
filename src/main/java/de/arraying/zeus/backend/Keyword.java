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
public enum Keyword {

    BOOLEAN_TRUE("true"),
    BOOLEAN_FALSE("false"),
    VAR_CONSTANT("const"),
    VAR_MUTABLE("mut"),
    CONTROL_SLEEP("sleep"),
    CONTROL_STOP("stop"),
    CONTROL_END("end"),
    CONDITIONAL_IF("if"),
    CONDITIONAL_ELSE("else"),
    CONDITIONAL_ELSE_IF("elif");

    /**
     * Checks whether or not a provided identifier is a keyword.
     * @param identifier The identifier.
     * @return True if it is, false otherwise.
     */
    public static boolean isKeyword(String identifier) {
        for(Keyword keyword : Keyword.values()) {
            if(keyword.identifier.equals(identifier)) {
                return true;
            }
        }
        return false;
    }

    private final String identifier;

    /**
     * Sets the keyword's identifier.
     * @param identifier The identifier.
     */
    Keyword(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets the keyword's identifier.
     * @return The identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

}
