package de.arraying.zeus.impl;

import de.arraying.zeus.backend.Patterns;
import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.utils.ZeusVariableUtil;
import de.arraying.zeus.variable.VariableType;
import de.arraying.zeus.variable.ZeusVariable;

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
public class ZeusVariableImpl implements ZeusVariable {

    private final VariableType type;
    private final String identifier;
    private final Object value;

    /**
     * Creates a new variable.
     * @param type The type of the variable.
     * @param identifier The variable identifier.
     *                   This identifier will be checked if it is
     * @param value The value of the variable.
     */
    public ZeusVariableImpl(VariableType type, String identifier, Object value)
            throws ZeusException {
        if(type == null) {
            throw new ZeusException("The variable type cannot be null.");
        }
        this.type = type;
        if(identifier == null) {
            throw  new ZeusException("The variable identifier cannot be null.");
        }
        Pattern identifierPattern = Pattern.compile(Patterns.IDENTIFIER.getStringPattern());
        if(!identifierPattern.matcher(identifier).find()) {
            throw new ZeusException("The variable identifier passed in is not a valid identifier (\"" + identifier + "\").");
        }
        this.identifier = identifier;
        if(value == null) {
            throw new ZeusException("The variable value cannot be null.");
        }
        if(!ZeusVariableUtil.isValidValue(value.getClass())) {
            throw new ZeusException("The variable value passed in is not a supported variable value.");
        }
        this.value = value;
    }

    /**
     * Gets the variable type.
     * @return The variable type.
     */
    public VariableType type() {
        return type;
    }

    /**
     * Gets the variable identifier.
     * @return The variable identifier.
     */
    public String identifier() {
        return identifier;
    }

    /**
     * Gets the variable value.
     * @return The value.
     */
    public Object value() {
        return value;
    }

}
