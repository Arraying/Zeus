package de.arraying.zeus.std.component.components;

import de.arraying.zeus.backend.Keyword;
import de.arraying.zeus.backend.Patterns;
import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.backend.ZeusMethod;
import de.arraying.zeus.impl.ZeusTaskImpl;
import de.arraying.zeus.std.component.ZeusStandardComponent;
import de.arraying.zeus.token.Token;
import de.arraying.zeus.utils.ZeusVariableUtil;
import de.arraying.zeus.variable.VariableType;
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
public class StandardComponentVariable implements ZeusStandardComponent {

    /**
     * Invokes the component.
     * @param task The impl of the task in order to access some impl-only methods.
     * @param tokens An array of all tokens that have been tokenized.
     * @param lineNumber The line number.
     * @throws ZeusException If an error occurs.
     */
    @Override
    public void invoke(ZeusTaskImpl task, Token[] tokens, int lineNumber)
            throws ZeusException {
        Token first = tokens[0];
        if(first.getType() != Patterns.IDENTIFIER) {
            return;
        }
        VariableType type = VariableType.fromIdentifier(first.getToken());
        if(type == null) {
            if(tokens.length >= 3
                    && isEquals(tokens[1])) {
                ZeusVariable variable = task.getVariable(first.getToken());
                if(variable == null) {
                    throw new ZeusException("Cannot update non-existent variable \"" + first.getToken() + "\".", lineNumber);
                }
                Object value = handleValue(task, tokens, 2, lineNumber);
                task.updateVariable(ZeusVariableUtil.createVariable(variable.type(), variable.identifier(), value), lineNumber);
            }
            return;
        }
        if(tokens.length < 2) {
            throw new ZeusException("Expected an identifier for variable declaration.", lineNumber);
        }
        Token identifier = tokens[1];
        if(identifier.getType() != Patterns.IDENTIFIER) {
            throw new ZeusException("Expected an identifier for variable declaration, instead found " +
                    identifier.getType() + ".", lineNumber);
        }
        String name = identifier.getToken();
        if(Keyword.isKeyword(name)) {
            throw new ZeusException("The variable name \"" + name + "\" is invalid as it is a keyword.", lineNumber);
        }
        if(tokens.length < 3) {
            throw new ZeusException("Expected an equals sign for variable declaration.", lineNumber);
        }
        Token equals = tokens[2];
        if(equals.getType() != Patterns.TOKEN
                || !equals.getToken().equals("=")) {
            throw new ZeusException("Expected a literal token for variable token, instead found " +
                    equals.getType() + ".", lineNumber);
        }
        if(!equals.getToken().equals("=")) {
            throw new ZeusException("Expected the token \"=\" for variable token, instead found \"" +
                    equals.getToken() + "\".", lineNumber);
        }
        if(tokens.length < 4) {
            throw new ZeusException("Expected a variable value.", lineNumber);
        }
        Object variableValue = handleValue(task, tokens, 3, lineNumber);
        task.updateVariable(ZeusVariableUtil.createVariable(type, name, variableValue), lineNumber);
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
    private Object handleValue(ZeusTaskImpl task, Token[] tokens, int valueIndex, int lineNumber)
            throws ZeusException {
        Token value = tokens[valueIndex];
        Object variableValue;
        if(value.getType().isDataType()) {
            variableValue = ZeusVariableUtil.getVariableValue(value);
            if(variableValue == null) {
                throw new ZeusException("Invalid variable value.", lineNumber);
            }
        } else if(value.getType() == Patterns.IDENTIFIER) {
            if(tokens.length == 4) {
                ZeusVariable variable = task.getVariable(value.getToken());
                if(variable == null) {
                    throw new ZeusException("The variable \"" + value.getToken() + "\" defined in the variable declaration does not exist.", lineNumber);
                }
                variableValue = variable.value();
            } else {
                Token[] methodTokens = Arrays.copyOfRange(tokens, valueIndex, tokens.length);
                ZeusMethod method = ZeusMethod.processMethod(task, methodTokens, lineNumber);
                if(method.getReturnValue() == null) {
                    throw new ZeusException("The method in variable declaration returned null.", lineNumber);
                }
                variableValue = method.getReturnValue();
            }
        } else {
            throw new ZeusException("Found unexpected token as a variable value.", lineNumber);
        }
        return variableValue;
    }

    /**
     * Whether or not the token is an equals ("=") token.
     * @param token The token.
     * @return True if it is, false otherwise.
     */
    private boolean isEquals(Token token) {
        return token.getType() == Patterns.TOKEN
                && token.getToken().equals("=");
    }

}
