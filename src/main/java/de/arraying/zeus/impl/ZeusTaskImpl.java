package de.arraying.zeus.impl;

import de.arraying.zeus.runtime.ZeusRuntime;
import de.arraying.zeus.runtime.ZeusTask;

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
public class ZeusTaskImpl implements ZeusTask {

    private final Thread current;
    private final ZeusRuntime runtime;
    private final String[] code;
    private int currentLine = 0;

    /**
     * Creates a new task.
     * @param runtime The runtime instance.
     * @param code The code to evaluate.
     */
    ZeusTaskImpl(ZeusRuntime runtime, String[] code) {
        this.current = new Thread(this);
        this.runtime = runtime;
        this.code = code;
    }

    /**
     * Runs the task.
     */
    @Override
    public void run() {
        while(!current.isInterrupted()
                && currentLine < code.length) {
            System.out.println("Interrupted: " + current.isInterrupted());
            System.out.println("Index " + currentLine + ": \"" + code[currentLine] + "\"");
            currentLine++;
        }
        System.out.println("Reached the end");
    }

    /**
     * Starts the evaluation of the code.
     */
    @Override
    public void evaluate() {
        current.start();
    }

    /**
     * Kills the current task.
     */
    @Override
    public void kill() {
        current.interrupt();
    }

}
