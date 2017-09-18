package de.arraying.zeus.utils;

import de.arraying.zeus.backend.Patterns;
import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.backend.ZeusMethod;
import de.arraying.zeus.impl.ZeusTaskImpl;
import de.arraying.zeus.token.Token;
import de.arraying.zeus.variable.ZeusVariable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
public class ZeusMethodUtil {

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
        if(!method.getReturnType().equals(Void.TYPE)) {
            return ZeusVariableUtil.isValidValue(method.getReturnType());
        }
        Class<?>[] parameters = method.getParameterTypes();
        if(parameters.length == 0) {
            return true;
        }
        for(Class parameter : parameters) {
            if(parameter.equals(Object.class)) {
                continue;
            }
            if(!ZeusVariableUtil.isValidValue(parameter)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the method invocation is valid.
     * @param tokens An array of tokens for that line.
     * @return True if it is, false otherwise.
     */
    public static boolean isValidMethodInvocation(Token[] tokens) {
        return tokens.length >= 3
                && isBracket(tokens[1], true)
                && isBracket(tokens[tokens.length-1], false);
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
     * Processes the method.
     * @param task The current task, used to retrieve variables and methods.
     * @param rawTokens An array of all the line's tokens.
     * @param lineNumber The current line number, used in exception handling.
     * @return A ZeusMethod object which contains the method invocation's return value.
     * @throws ZeusException If an exception occurs. Or the empire strikes back. Either are bad.
     */
    public static ZeusMethod processMethod(ZeusTaskImpl task, Token[] rawTokens, int lineNumber)
            throws ZeusException {
        Token[] tokens = Arrays.copyOfRange(rawTokens, 2, rawTokens.length-1);
        List<Object> parameters = new LinkedList<>();
        boolean expectingComma = false;
        for(int i = 0; i < tokens.length; i++) {
            Token token = tokens[i];
            if(expectingComma) {
                if(!isComma(token)) {
                    throw new ZeusException("Expected comma in the parameters of the method invocation.", lineNumber);
                } else {
                    expectingComma = false;
                    continue;
                }
            }
            Object value;
            if(token.getType() == Patterns.IDENTIFIER) {
                int next = nextBracket(i, tokens);
                if(i + 1 < tokens.length
                        &&isBracket(tokens[i + 1], true)
                        && next != -1) {
                    Token[] method = Arrays.copyOfRange(tokens, i, next + 1);
                    ZeusMethod result = processMethod(task, method, lineNumber);
                    if(!result.isValid()) {
                        throw new ZeusException("The embedded method provided in the method is invalid.", lineNumber);
                    }
                    if(result.getReturnValue() == null) {
                        throw new ZeusException("The embedded method provided in the method illegally returned null.", lineNumber);
                    }
                    value = result.getReturnValue();
                    i = next;
                } else {
                    ZeusVariable variable = task.getVariable(token.getToken());
                    if(variable == null) {
                        throw new ZeusException("The variable \"" + token.getToken() + "\" defined in the method parameters does not exist.", lineNumber);
                    }
                    value = variable.value();
                }
            } else {
                if(!token.getType().isDataType()) {
                    throw new ZeusException("Expected a type value, found other token instead (\"" + token.getToken() + "\").", lineNumber);
                }
                Object givenValue = ZeusVariableUtil.getVariableValue(token);
                if(givenValue == null) {
                    throw new ZeusException("The value given as one of the method parameters is invalid (\"" + token.getToken() + "\").", lineNumber);
                }
                value = givenValue;
            }
            parameters.add(value);
            expectingComma = true;
        }
        String identifier = rawTokens[0].getToken();
        Method method = task.getMethod(identifier);
        if(method == null) {
            throw new ZeusException("A method with the identifier \"" + identifier + "\" does not exist.", lineNumber);
        }
        try {
            Object[] params = parameters.toArray(new Object[parameters.size()]);
            Object invocationObject = method.getDeclaringClass().newInstance();
            Object returnValue = method.invoke(invocationObject, (Object[]) params);
            return new ZeusMethod(returnValue, true);
        } catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
            throw new ZeusException("Encountered " + exception.toString(), lineNumber);
        }
    }

    /**
     * Whether or not the provided token is a comma.
     * @param token The token.
     * @return True if it is, false otherwise.
     */
    private static boolean isComma(Token token) {
        return token.getType() == Patterns.TOKEN
                && token.getToken().equals(",");
    }

    /**
     * Checks whether or not a token is a bracket.
     * @param token The token.
     * @param open True: "(", false: ")".
     * @return True if it is, false otherwise.
     */
    private static boolean isBracket(Token token, boolean open) {
        String bracket = open ? "(" : ")";
        return token.getType() == Patterns.TOKEN
                && token.getToken().equals(bracket);
    }

    /**
     * Gets the next closed bracket.
     * @param current The current index concerning the token iteration during method processing.
     * @param tokens An array of tokens (sub array).
     * @return The index of the next bracket, or -1 if it does not exist.
     */
    private static int nextBracket(int current, Token[] tokens) {
        int open = 0;
        int closed = 0;
        for(int i = current; i < tokens.length; i++) {
            Token token = tokens[i];
            if(isBracket(token, true)) {
                open++;
            }
            if(isBracket(token, false)
                    && ++closed >= open) {
                return i;
            }
        }
        return -1;
    }

}
