package de.arraying.zeus.event;

import de.arraying.zeus.event.events.SleepEvent;
import de.arraying.zeus.event.events.StopEvent;
import de.arraying.zeus.event.events.VariableDeclareEvent;
import de.arraying.zeus.event.events.VariableReassignEvent;
import de.arraying.zeus.event.generic.ZeusEvent;

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
@SuppressWarnings({"WeakerAccess", "unused"})
public class ZeusEventListener {

    /**
     * When any event occurs.
     * @param event The event.
     */
    public void onGenericEvent(ZeusEvent event) {}

    /**
     * When the code is slept.
     * @param event The event.
     */
    public void onSleep(SleepEvent event) {}

    /**
     * When the code is stopped.
     * @param event The event.
     */
    public void onStop(StopEvent event) {}

    /**
     * When a new variable is declared.
     * @param event The event.
     */
    public void onVariableDeclaration(VariableDeclareEvent event) {}

    /**
     * When a variable is re-assigned to another value.
     * @param event The event.
     */
    public void onVariableReassign(VariableReassignEvent event) {}

    /**
     * This method is run when any event occurs; it will then call individual events.
     * @param event The event.
     */
    public final void onEvent(ZeusEvent event) {
        onGenericEvent(event);
        if(event instanceof SleepEvent) {
            onSleep((SleepEvent) event);
        } else if(event instanceof StopEvent) {
            onStop((StopEvent) event);
        } else if(event instanceof VariableDeclareEvent) {
            onVariableDeclaration((VariableDeclareEvent) event);
        } else if(event instanceof VariableReassignEvent) {
            onVariableReassign((VariableReassignEvent) event);
        }
    }

}
