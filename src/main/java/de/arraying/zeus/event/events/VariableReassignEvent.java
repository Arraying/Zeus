package de.arraying.zeus.event.events;

import de.arraying.zeus.event.generic.VariableEvent;
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
public class VariableReassignEvent extends VariableEvent {

    /**
     * Creates a new generic variable event.
     * @param variable The variable.
     * @param lineNumber The line number.
     */
    public VariableReassignEvent(ZeusVariable variable, int lineNumber) {
        super(variable, lineNumber);
    }

}
