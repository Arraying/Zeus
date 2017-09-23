package de.arraying.zeus.utils;

import de.arraying.zeus.backend.Patterns;
import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.impl.ZeusVariableImpl;
import de.arraying.zeus.token.Token;
import de.arraying.zeus.variable.VariableType;
import de.arraying.zeus.variable.VariableValues;
import de.arraying.zeus.variable.ZeusVariable;

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
public class ZeusVariableUtil {

    /**
     * Method to create a new variable.
     * @param type The variable type.
     * @param identifier The variable identifier.
     * @param value The variable value.
     * @return A valid ZeusVariable object.
     * @throws ZeusException If an exception occurs creating the variable.
     */
    public static ZeusVariable createVariable(VariableType type, String identifier, Object value)
            throws ZeusException {
        return new ZeusVariableImpl(type, identifier, value);
    }

    /**
     * Checks if the class given is a valid variable value class.
     * @param clazz The class.
     * @return True if it is, false otherwise.
     */
    public static boolean isValidValue(Class<?> clazz) {
        for(VariableValues value : VariableValues.values()) {
            if(value.getValueClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a variable value from a token.
     * This is presuming that the token is not another variable identifier or method call.
     * @param token The token.
     * @return An object value or null if the token is invalid for a variable value.
     * @throws ZeusException If an error occurs.
     */
    public static Object getVariableValue(Token token)
            throws ZeusException {
        try {
            Patterns pattern = token.getType();
            String value = token.getToken();
            switch(pattern) {
                case TYPE_INT:
                    return Integer.valueOf(value);
                case TYPE_BOOL:
                    return Boolean.valueOf(value);
                case TYPE_LONG:
                    return Long.valueOf(value.substring(0, value.length()-1));
                case TYPE_DOUBLE:
                    return Double.valueOf(value);
                case TYPE_STRING:
                    return value.substring(1, value.length()-1);
                default:
                    return null;
            }
        } catch(Exception exception) {
            throw new ZeusException("Encountered " + exception.toString());
        }
    }

}
