package de.arraying.zeus.impl;

import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.runtime.ZeusTask;
import de.arraying.zeus.std.component.ZeusComponent;
import de.arraying.zeus.token.Token;
import de.arraying.zeus.token.Tokenizer;
import de.arraying.zeus.variable.VariableType;
import de.arraying.zeus.variable.ZeusVariable;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Consumer;

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
    private final ZeusRuntimeImpl runtime;
    private final Map<String, ZeusVariable> variables;
    private final Consumer<ZeusException> error;
    private final String[] code;
    private int currentIndex = 0;

    /**
     * Creates a new task.
     * @param runtime The runtime instance.
     * @param variables A map of all predefined variables.
     * @param code The code to evaluate.
     */
    ZeusTaskImpl(ZeusRuntimeImpl runtime, Map<String, ZeusVariable> variables, Consumer<ZeusException> error, String[] code) {
        this.current = new Thread(this);
        this.runtime = runtime;
        this.variables = variables;
        this.error = error;
        this.code = code;
    }

    /**
     * Runs the task.
     */
    @Override
    public void run() {
        while(!runtime.isShutdown()
                && !current.isInterrupted()
                && currentIndex < code.length) {
            try {
                String line = code[currentIndex];
                currentIndex++;
                if(line.isEmpty()) {
                    continue;
                }
                int lineNumber = currentIndex;
                Tokenizer tokenizer = new Tokenizer(line, lineNumber);
                if(tokenizer.getTokens().length == 0) {
                    continue;
                }
                for(ZeusComponent component : runtime.getComponents()) {
                    component.invoke(this, tokenizer.getTokens(), lineNumber);
                }
            } catch(ZeusException exception) {
                error(exception);
            }
        }
    }

    /**
     * Starts the evaluation of the code.
     */
    @Override
    public void evaluate() {
        if(!current.isAlive()) {
            current.start();
        }
    }

    /**
     * Kills the current task.
     */
    @Override
    public void kill() {
        current.interrupt();
    }

    /**
     * Gets a method by identifier.
     * @param identifier The identifier or the method.
     * @return A method or null if it does not exist.
     */
    public Method getMethod(String identifier) {
        return runtime.getMethods().get(identifier);
    }

    /**
     * Gets a variable by identifier.
     * @param identifier The identifier.
     * @return A variable or null if it does not exist.
     */
    public ZeusVariable getVariable(String identifier) {
        return variables.get(identifier);
    }

    /**
     * Updates a variable.
     * Updating means defining it, or if it already exist overwriting it.
     * @param variable The variable.
     * @param lineNumber The line number.
     * @throws ZeusException If the variable already exists and is a constant.
     */
    public void updateVariable(ZeusVariable variable, int lineNumber)
            throws ZeusException {
        ZeusVariable existing = variables.get(variable.identifier());
        if(existing != null
                && existing.type() == VariableType.CONSTANT) {
            throw new ZeusException("The variable \"" + variable.identifier() + "\" cannot be updated as it is a constant at line "
                    + lineNumber + ".");
        }
        variables.put(variable.identifier(), variable);
    }

    /**
     * Quick method to error without having to do nullchecks every time.
     * And writing kill(). Because that's so much effort. A whole 7 characters.
     * @param exception The exception.
     */
    private void error(ZeusException exception) {
        if(error != null) {
            error.accept(exception);
        }
        kill();
    }

}
