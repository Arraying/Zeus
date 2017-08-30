package de.arraying.zeus.std;

import de.arraying.zeus.backend.ZeusMethod;
import de.arraying.zeus.backend.ZeusUtil;

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
public class Out implements Standard {

    /**
     * Prints a string to the console. Appends a new line.
     * @param toPrint The string to print.
     */
    @ZeusMethod(methodType = ZeusMethod.Type.STD)
    public void println(String toPrint) {
        System.out.println(toPrint);
    }

    /**
     * Prints a string to the console.
     * @param toPrint The string to print.
     */
    @ZeusMethod(methodType = ZeusMethod.Type.STD)
    public void print(String toPrint) {
        System.out.print(toPrint);
    }

    /**
     * Prints Zeus info to the console.
     */
    @ZeusMethod(methodType = ZeusMethod.Type.STD)
    public void info() {
        System.out.println("Zeus v" + ZeusUtil.VERSION + " by " + ZeusUtil.AUTHOR + ".");
    }

}
