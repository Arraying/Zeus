package de.arraying.zeus.backend;

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
public class ZeusException extends Exception {

    private final int lineNumber;

    /**
     * Creates a new Zeus exception.
     * @param message The exception message.
     */
    public ZeusException(String message) {
        this(message, -1);
    }

    /**
     * Creates a new Zeus exception.
     * @param message The exception message.
     * @param lineNumber The line number.
     */
    public ZeusException(String message, int lineNumber) {
        super(message);
        this.lineNumber = lineNumber;
    }

    /**
     * Gets the line number.
     * @return The line number, or -1 if there is no line number.
     */
    public int getLineNumber() {
        return lineNumber;
    }

}
