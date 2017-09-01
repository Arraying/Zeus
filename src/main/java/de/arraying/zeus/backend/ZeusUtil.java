package de.arraying.zeus.backend;

import de.arraying.zeus.Zeus;
import de.arraying.zeus.impl.ZeusTaskImpl;
import de.arraying.zeus.impl.ZeusVariableImpl;
import de.arraying.zeus.token.Token;
import de.arraying.zeus.variable.VariableType;
import de.arraying.zeus.variable.VariableValues;
import de.arraying.zeus.variable.ZeusVariable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;

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
@SuppressWarnings("unused")
public class ZeusUtil {

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
     * Checks if an identifier is a keyword.
     * @param identifier The identifier.
     * @return True if it is, false otherwise.
     */
    public static boolean isKeyword(String identifier) {
        for(String keyword : Zeus.KEYWORDS) {
            if(identifier.equals(keyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a variable value from a token.
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
                    return Long.valueOf(value.substring(1));
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

    /**
     * Gets the values of all parameters specified in the method.
     * @param task The current task, used to access variables and such.
     * @param tokens An array of tokens, the ones for the line.
     * @param lineNumber The line number, used for the exception.
     * @return An object array of method parameters.
     * @throws ZeusException If an error occurs.
     */
    public static Object[] getMethodParameters(ZeusTaskImpl task, Token[] tokens, int lineNumber)
            throws ZeusException {
        boolean expectingComma = false;
        LinkedList<Object> values = new LinkedList<>();
        for(int i = 2; i < tokens.length-1; i++) {
            if(expectingComma) {
                if(tokens[i].getType() != Patterns.TOKEN
                        && !tokens[i].getToken().equals(",")) {
                    throw new ZeusException("Expected comma in the parameters of the method invocation at line " + lineNumber + ".");
                } else {
                    expectingComma = false;
                    continue;
                }
            }
            Token token = tokens[i];
            Object value;
            if(token.getType() == Patterns.IDENTIFIER) {
                ZeusVariable variable = task.getVariable(token.getToken());
                if(variable == null) {
                    throw new ZeusException("The variable \"" + token.getToken() + "\" specified in the method invocation parameters at line " +
                            lineNumber + " does not exist.");
                }
                value = variable.value();
            } else {
                Object givenValue = getVariableValue(token);
                if(givenValue == null) {
                    throw new ZeusException("The value given as one of the method parameters at line " + lineNumber + " is invalid.");
                }
                value = givenValue;
            }
            values.add(value);
            expectingComma = true;
        }
        return values.toArray(new Object[values.size()]);
    }

    /**
     * Checks if an object is a supported variable value.
     * @param value The object value.
     * @return True if it is, false otherwise.
     */
    public static boolean isValidVariableValue(Object value) {
        return isValidValue(value.getClass());
    }

    /**
     * Checks if a method is valid.
     * @param method The method.
     * @return True if it is, false otherwise.
     */
    public static boolean isValidMethod(Method method) {
        if(method.isVarArgs()
                || !Modifier.isPublic(method.getModifiers())) {
            return false;
        }
        if(!method.getReturnType().equals(Void.TYPE)
                && !isValidValue(method.getReturnType().getClass())) {
            return false;
        }
        Class<?>[] parameters = method.getParameterTypes();
        if(parameters.length == 0) {
            return true;
        }
        for(Class parameter : parameters) {
            if(!isValidValue(parameter)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the class can be instantiated.
     * @param provided The class provided.
     * @return True if it is, false otherwise.
     */
    public static boolean isValidMethodContainer(Class provided) {
        try {
            provided.newInstance();
            return true;
        } catch(InstantiationException | IllegalAccessException exception) {
            return false;
        }
    }

    /**
     * Checks if the class given is a valid variable value class.
     * @param clazz The class.
     * @return True if it is, false otherwise.
     */
    private static boolean isValidValue(Class<?> clazz) {
        for(VariableValues value : VariableValues.values()) {
            if(value.getValueClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }

}
