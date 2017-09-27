package de.arraying.zeus.std.component.components.condition;

import de.arraying.zeus.backend.Keyword;
import de.arraying.zeus.backend.Patterns;
import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.impl.ZeusTaskImpl;
import de.arraying.zeus.std.component.ZeusScopeComponent;
import de.arraying.zeus.token.Token;
import de.arraying.zeus.utils.ZeusUtil;

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
public class IfComponent implements ZeusScopeComponent {

    /**
     * Invokes the component.
     * @param task The impl of the task in order to access some impl only methods.
     * @param tokens An array of all tokens that have been tokenized.
     * @param lineNumber The line number.
     * @return True if successful, false otherwise.
     * @throws ZeusException if an error occurs.
     */
    @Override
    public boolean invoke(ZeusTaskImpl task, Token[] tokens, int lineNumber)
            throws ZeusException {
        Token identifier = tokens[0];
        if(identifier.getType() != Patterns.IDENTIFIER
                || !identifier.getToken().equals(Keyword.CONDITIONAL_IF.getIdentifier())) {
            return false;
        }
        if(tokens.length < 2) {
            throw new ZeusException("Expected a condition for if statement.", lineNumber);
        }
        Object value = ZeusUtil.handleValue(task, tokens, 1, lineNumber);
        if(!(value instanceof Boolean)) {
            throw new ZeusException("The condition in the if statement does not return a boolean.", lineNumber);
        }
        boolean condition = (boolean) value;
        int scope = task.getScope() + 1;
        task.setParsing(scope, (condition && task.isParsing(task.getScope())));
        task.setScope(scope);
        return true;
    }

}
