package de.arraying.zeus.utils;

import de.arraying.zeus.backend.Patterns;
import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.backend.ZeusMethod;
import de.arraying.zeus.impl.ZeusTaskImpl;
import de.arraying.zeus.token.Token;
import de.arraying.zeus.variable.ZeusVariable;

import java.util.Arrays;

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
public class ZeusUtil {

    /**
     * The identifier to check for "<".
     */
    public static final int COMP_LESS = -2;

    /**
     * The identifier to check for "<=".
     */
    public static final int COMP_LESS_OR_EQUAL = -1;

    /**
     * The identifier to check for ">=".
     */
    public static final int COMP_MORE_OR_EQUAL = 1;

    /**
     * The indentifier to check for ">".
     */
    public static final int COMP_MORE = 2;

    /**
     * Compares two number with each other.
     * @param a The first number.
     * @param b The second number.
     * @param comparison The type of comparison.
     * @return Whether or not the comparison is met.
     * Could also return false if the passed in values are not of the same data type.
     */
    public static boolean compare(Number a, Number b, int comparison) {
        if(a instanceof Integer
                && b instanceof Integer) {
            switch(comparison) {
                case COMP_LESS:
                    return (int) a < (int) b;
                case COMP_LESS_OR_EQUAL:
                    return (int) a <= (int) b;
                case COMP_MORE_OR_EQUAL:
                    return (int) a >= (int) b;
                case COMP_MORE:
                    return (int) a > (int) b;
            }
        } else if(a instanceof Double
                && b instanceof Double) {
            switch(comparison) {
                case COMP_LESS:
                    return (double) a < (double) b;
                case COMP_LESS_OR_EQUAL:
                    return (double) a <= (double) b;
                case COMP_MORE_OR_EQUAL:
                    return (double) a >= (double) b;
                case COMP_MORE:
                    return (double) a > (double) b;
            }
            return false;
        } else if(a instanceof Long
                && b instanceof Long) {
            switch(comparison) {
                case COMP_LESS:
                    return (long) a < (long) b;
                case COMP_LESS_OR_EQUAL:
                    return (long) a <= (long) b;
                case COMP_MORE_OR_EQUAL:
                    return (long) a >= (long) b;
                case COMP_MORE:
                    return (long) a > (long) b;
            }
            return false;
        }
        return false;
    }

    /**
     * Handles the variable value and gets an Object as a value.
     * @param task The task impl. Used to access impl-only methods.
     * @param tokens An array of all tokens.
     * @param valueIndex The index where the value starts.
     * @param lineNumber The line number.
     * @return An object.
     * @throws ZeusException If an error occurs.
     */
    public static Object handleValue(ZeusTaskImpl task, Token[] tokens, int valueIndex, int lineNumber)
            throws ZeusException {
        Token value = tokens[valueIndex];
        Object variableValue;
        if(value.getType().isDataType()) {
            variableValue = ZeusVariableUtil.getVariableValue(value);
            if(variableValue == null) {
                throw new ZeusException("Invalid value.", lineNumber);
            }
        } else if(value.getType() == Patterns.IDENTIFIER) {
            if(tokens.length == (valueIndex + 1)) {
                ZeusVariable variable = task.getVariable(value.getToken());
                if(variable == null) {
                    throw new ZeusException("The variable \"" + value.getToken() + "\" defined does not exist.", lineNumber);
                }
                variableValue = variable.value();
            } else {
                Token[] methodTokens = Arrays.copyOfRange(tokens, valueIndex, tokens.length);
                ZeusMethod method = ZeusMethod.processMethod(task, methodTokens, lineNumber);
                if(method.getReturnValue() == null) {
                    throw new ZeusException("The method in declaration returned null.", lineNumber);
                }
                variableValue = method.getReturnValue();
            }
        } else {
            throw new ZeusException("Found unexpected token as a value.", lineNumber);
        }
        return variableValue;
    }

}
