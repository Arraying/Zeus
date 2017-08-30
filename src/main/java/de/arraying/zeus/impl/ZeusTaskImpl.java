package de.arraying.zeus.impl;

import de.arraying.zeus.runtime.ZeusRuntime;
import de.arraying.zeus.runtime.ZeusTask;
import de.arraying.zeus.token.Token;
import de.arraying.zeus.token.Tokenizer;

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
    private int currentIndex = 0;

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
                && currentIndex < code.length) {
            Tokenizer tokenizer = new Tokenizer(code[currentIndex], currentIndex+1);
            for(Token token : tokenizer.getTokens()) {
                System.out.println("Token: \"" + token + "\"");
            }
            currentIndex++;
        }
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
