package de.arraying.zeus.variable;

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
public enum VariableType {

    /**
     * A constant variable.
     * Constants can only be declared once and they will not change.
     */
    CONSTANT("const"),

    /**
     * A mutable variable.
     * This variable can be reassigned as often as wanted.
     */
    MUTABLE("mut");

    /**
     * Gets a variable type from its string identifier.
     * @param identifier The string identifier.
     * @return A type enumeration or null.
     */
    public static VariableType fromIdentifier(String identifier) {
        if(identifier == null) {
            return null;
        }
        for(VariableType type : values()) {
            if(type.identifier.equals(identifier)) {
                return type;
            }
        }
        return null;
    }

    private final String identifier;

    /**
     * Sets the identifier for a variable type.
     * The identifier is the string used to determine the type of variable.
     * @param identifier The identifier.
     */
    VariableType(String identifier) {
        this.identifier = identifier.toLowerCase();
    }

}
