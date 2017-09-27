package de.arraying.zeus.std.component.components;

import de.arraying.zeus.backend.Keyword;
import de.arraying.zeus.backend.Patterns;
import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.impl.ZeusTaskImpl;
import de.arraying.zeus.std.component.ZeusStandardComponent;
import de.arraying.zeus.token.Token;
import de.arraying.zeus.utils.ZeusUtil;
import de.arraying.zeus.utils.ZeusVariableUtil;
import de.arraying.zeus.variable.VariableType;
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
public class VariableComponent implements ZeusStandardComponent {

    /**
     * Invokes the component.
     * @param task The impl of the task in order to access some impl-only methods.
     * @param tokens An array of all tokens that have been tokenized.
     * @param lineNumber The line number.
     * @return True if successful, false otherwise.
     * @throws ZeusException If an error occurs.
     */
    @Override
    public boolean invoke(ZeusTaskImpl task, Token[] tokens, int lineNumber)
            throws ZeusException {
        Token first = tokens[0];
        if(first.getType() != Patterns.IDENTIFIER) {
            return false;
        }
        VariableType type = VariableType.fromIdentifier(first.getToken());
        if(type == null) {
            if(tokens.length >= 3
                    && isEquals(tokens[1])) {
                ZeusVariable variable = task.getVariable(first.getToken());
                if(variable == null) {
                    throw new ZeusException("Cannot update non-existent variable \"" + first.getToken() + "\".", lineNumber);
                }
                Object value = ZeusUtil.handleValue(task, tokens, 2, lineNumber);
                task.updateVariable(ZeusVariableUtil.createVariable(variable.type(), variable.identifier(), value), lineNumber);
                return true;
            }
            return false;
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
        Object variableValue = ZeusUtil.handleValue(task, tokens, 3, lineNumber);
        task.updateVariable(ZeusVariableUtil.createVariable(type, name, variableValue), lineNumber);
        return true;
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
