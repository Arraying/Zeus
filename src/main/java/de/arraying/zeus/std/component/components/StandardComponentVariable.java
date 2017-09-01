package de.arraying.zeus.std.component.components;

import de.arraying.zeus.backend.Patterns;
import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.backend.ZeusUtil;
import de.arraying.zeus.impl.ZeusTaskImpl;
import de.arraying.zeus.std.component.ZeusStandardComponent;
import de.arraying.zeus.token.Token;
import de.arraying.zeus.variable.VariableType;

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
     * @param runtime The impl of the task in order to access some impl only methods.
     * @param tokens An array of all tokens that have been tokenized.
     * @param lineNumber The line number.
     * @throws ZeusException If an error occurs.
     */
    @Override
    public void invoke(ZeusTaskImpl runtime, Token[] tokens, int lineNumber)
            throws ZeusException {
        Token variableType = tokens[0];
        if(variableType.getType() != Patterns.IDENTIFIER) {
            return;
        }
        VariableType type = VariableType.fromIdentifier(variableType.getToken());
        if(type == null) {
            return;
        }
        if(tokens.length < 2) {
            throw new ZeusException("Expected an identifier for variable declaration at line " + lineNumber + ".");
        }
        Token identifier = tokens[1];
        if(identifier.getType() != Patterns.IDENTIFIER) {
            throw new ZeusException("Expected an identifier for variable declaration, instead found " +
                    identifier.getType() + " at line " + lineNumber + ".");
        }
        String name = identifier.getToken();
        if(ZeusUtil.isKeyword(name)) {
            throw new ZeusException("The variable name \"" + name + "\" is invalid as it is a keyword.");
        }
        if(tokens.length < 3) {
            throw new ZeusException("Expected an equals sign for variable declaration at line " + lineNumber + ".");
        }
        Token equals = tokens[2];
        if(equals.getType() != Patterns.TOKEN) {
            throw new ZeusException("Expected a literal token for variable token, instead found " +
                    equals.getType() + " at line " + lineNumber + ".");
        }
        if(!equals.getToken().equals("=")) {
            throw new ZeusException("Expected the token \"=\" for variable token, instead found \"" +
                    equals.getToken() + "\" at line " + lineNumber + ".");
        }
        if(tokens.length < 4) {
            throw new ZeusException("Expected a variable value at line " + lineNumber + ".");
        }
        Token value = tokens[3];
        Object variableValue = ZeusUtil.getVariableValue(value);
        if(variableValue == null) {
            throw new ZeusException("Invalid variable value at line " + lineNumber + ".");
        }
        runtime.updateVariable(ZeusUtil.createVariable(type, name, variableValue), lineNumber);
    }

}
