package de.arraying.zeus.event.events;

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
public class SleepEvent extends ZeusEvent {

    private final long duration;

    /**
     * Creates a new sleep event.
     * @param duration The duration to sleep for.
     * @param lineNumber The line number.
     */
    public SleepEvent(long duration, int lineNumber) {
        super(lineNumber);
        this.duration = duration;
    }

    /**
     * Gets the duration that the code will sleep for.
     * @return A duration in milliseconds.
     */
    public long getDuration() {
        return duration;
    }

}
