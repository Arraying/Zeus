package de.arraying.zeus.standard.method.methods;

import de.arraying.zeus.Zeus;
import de.arraying.zeus.backend.annotations.ZeusStandard;
import de.arraying.zeus.standard.method.ZeusStandardMethod;

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
@SuppressWarnings("unused")
public @ZeusStandard class OutputMethods implements ZeusStandardMethod {

    /**
     * Prints an Object to the console. Appends a new line.
     * @param toPrint The object to print.
     * @return The input.
     */
    public Object println(Object toPrint) {
        System.out.println(toPrint);
        return toPrint;
    }

    /**
     * Prints an Object to the console.
     * @param toPrint The object  to print.
     * @return The input.
     */
    public Object print(Object toPrint) {
        System.out.print(toPrint);
        return toPrint;
    }

    /**
     * Prints Zeus info to the console.
     */
    public void info() {
        System.out.println("Currently running Zeus, by " + Zeus.AUTHOR + ", version " + Zeus.VERSION + "." +
                "\nThanks to: " + String.join(", ", Zeus.CONTRIBUTORS) + ".");
    }

}
