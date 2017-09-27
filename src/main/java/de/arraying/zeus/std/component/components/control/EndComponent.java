package de.arraying.zeus.std.component.components.control;

import de.arraying.zeus.backend.Keyword;
import de.arraying.zeus.backend.Patterns;
import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.impl.ZeusTaskImpl;
import de.arraying.zeus.std.component.ZeusScopeComponent;
import de.arraying.zeus.token.Token;

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
public class EndComponent implements ZeusScopeComponent {

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
                || !identifier.getToken().equals(Keyword.CONTROL_END.getIdentifier())) {
            return false;
        }
        int decremented = task.getScope()-1;
        if(decremented < ZeusTaskImpl.DEFAULT_SCOPE) {
            throw new ZeusException("The end component is invalid, cannot decrement scope any further.", lineNumber);
        }
        task.setScope(decremented);
        task.setParsing(decremented, task.isParsing(decremented));
        return true;
    }
}
