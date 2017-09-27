package de.arraying.zeus.impl;

import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.backend.ZeusMethods;
import de.arraying.zeus.event.ZeusEventListener;
import de.arraying.zeus.runtime.ZeusRuntime;
import de.arraying.zeus.runtime.ZeusTask;
import de.arraying.zeus.std.component.ZeusComponent;
import de.arraying.zeus.variable.ZeusVariable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
public class ZeusRuntimeImpl implements ZeusRuntime {

    private ScheduledExecutorService executorService;
    private final Map<String, ZeusVariable> predefinedVariables;
    private final ZeusMethods methods;
    private final ZeusComponent[] components;
    private final ZeusEventListener[] eventListeners;
    private final int timeoutThreshold;
    private boolean isShutdown;

    /**
     * Creates a new Zeus runtime.
     * @param predefinedVariables The predefined variables.
     * @param methods A set of methods.
     * @param components An array of components to use.
     * @param eventListeners An array of event listeners.
     * @param timeoutThreshold The timeout threshold.
     */
    public ZeusRuntimeImpl(Map<String, ZeusVariable> predefinedVariables, ZeusMethods methods, ZeusComponent[] components, ZeusEventListener[] eventListeners, int timeoutThreshold) {
        this.predefinedVariables = predefinedVariables;
        this.methods = methods;
        this.components = components;
        this.eventListeners = eventListeners;
        this.timeoutThreshold = timeoutThreshold;
        this.isShutdown = false;
        if(timeoutThreshold != -1) {
            executorService = Executors.newScheduledThreadPool(5);
        }
    }

    /**
     * Evaluates the code.
     * @param code The code. Each array entry represents one line of code.
     * @return The evaluation task.
     * @throws ZeusException If an error occurs.
     */
    @Override
    public ZeusTask evaluate(String[] code, Consumer<ZeusException> error)
            throws ZeusException {
        if(isShutdown) {
            throw new ZeusException("The runtime has been shut down, thus no tasks can be evaluated.");
        }
        if(code == null) {
            throw new ZeusException("The code provided must not be null.");
        }
        if(code.length == 0) {
            throw new ZeusException("The code provided must not be empty.");
        }
        ZeusTaskImpl task = new ZeusTaskImpl(this, predefinedVariables, error, code);
        task.evaluate();
        if(executorService != null) {
            executorService.schedule(task::kill, timeoutThreshold, TimeUnit.MILLISECONDS);
        }
        return task;
    }

    /**
     * Evaluates the code.
     * @param file The file containing the code.
     * @return The evaluation task.
     * @throws ZeusException If an error occurs.
     */
    @Override
    public ZeusTask evaluate(File file, Consumer<ZeusException> error)
            throws ZeusException {
        if(file == null) {
            throw new ZeusException("The file provided must not be null.");
        }
        try {
            LinkedList<String> lines = new LinkedList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
            return evaluate(lines.toArray(new String[lines.size()]), error);
        } catch(IOException exception) {
            throw new ZeusException(exception.getMessage());
        }
    }

    /**
     * Gets all components.
     * @return An array of components.
     */
    ZeusComponent[] getComponents() {
        return components;
    }

    /**
     * Gets all methods.
     * @return A ZeusMethods object.
     */
    public ZeusMethods getMethods() {
        return methods;
    }

    /**
     * Gets all event listeners.
     * @return An array of event listeners.
     */
    ZeusEventListener[] getEventListeners() {
        return eventListeners;
    }

    /**
     * Checks whether or not the runtime has been shut down.
     * @return True if it has, false otherwise.
     */
    boolean isShutdown() {
        return isShutdown;
    }

    /**
     * Completely shuts down the runtime.
     * @throws ZeusException If the runtime has already been shutdown.
     */
    @Override
    public synchronized void shutdown()
            throws ZeusException {
        if(isShutdown) {
            throw new ZeusException("The runtime has already been shut down.");
        }
        executorService.shutdown();
        isShutdown = true;
    }


}
