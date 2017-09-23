package de.arraying.zeus.std.component.components;

import de.arraying.zeus.backend.Keyword;
import de.arraying.zeus.backend.Patterns;
import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.impl.ZeusTaskImpl;
import de.arraying.zeus.std.component.ZeusStandardComponent;
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
public class StandardComponentSleep implements ZeusStandardComponent {

    /**
     * Invokes the component.
     * @param task The impl of the task in order to access some impl only methods.
     * @param tokens An array of all tokens that have been tokenized.
     * @param lineNumber The line number.
     * @throws ZeusException if an error occurs.
     */
    @Override
    public void invoke(ZeusTaskImpl task, Token[] tokens, int lineNumber)
            throws ZeusException {
        Token identifier = tokens[0];
        if(identifier.getType() != Patterns.IDENTIFIER
                || !identifier.getToken().equals(Keyword.CONTROL_SLEEP.getIdentifier())) {
            return;
        }
        if(tokens.length != 2) {
            throw new ZeusException("Expected just the sleep keyword and a duration (long), found more/less.", lineNumber);
        }
        Token duration = tokens[1];
        if(duration.getType() != Patterns.TYPE_LONG) {
            throw new ZeusException("Expected a long for the sleep duration.", lineNumber);
        }
        long sleep;
        try {
            sleep = Long.valueOf(duration.getToken().substring(1));
        } catch(NumberFormatException exception) {
            sleep = -1;
        }
        if(sleep <= 0) {
            throw new ZeusException("The provided sleep duration is not a valid duration.", lineNumber);
        }
        try {
            Thread.sleep(sleep);
        } catch(InterruptedException exception) {
            throw new ZeusException("Attempted to sleep the code, but hit an interrupted exception.", lineNumber);
        }
    }

}
