package de.arraying.zeus.std.component;

import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.impl.ZeusTaskImpl;
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
public interface ZeusComponent {

    /**
     * Invokes the component. The component should then check if the provided
     * tokens have anything to do with it. The task manager will not do any sort
     * of checking, so everything is up to the component.
     * @param task The impl of the task in order to access some impl only methods.
     * @param tokens An array of all tokens that have been tokenized.
     * @param lineNumber The line number.
     * @throws ZeusException If an error occurs.
     */
    void invoke(ZeusTaskImpl task, Token[] tokens, int lineNumber) throws ZeusException;

}
