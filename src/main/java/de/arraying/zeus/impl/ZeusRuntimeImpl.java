package de.arraying.zeus.impl;

import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.backend.ZeusUtil;
import de.arraying.zeus.runtime.ZeusRuntime;
import de.arraying.zeus.runtime.ZeusTask;
import de.arraying.zeus.variable.VariableType;
import de.arraying.zeus.variable.ZeusVariable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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

    private final ConcurrentMap<String, ZeusVariable> variables;
    private final ConcurrentMap<String, Method> methods;

    /**
     * Creates a new Zeus runtime.
     * @param variables The predefined variables.
     * @param methods A set of methods.
     */
    public ZeusRuntimeImpl(Map<String, ZeusVariable> variables, Map<String, Method> methods) {
        this.variables = new ConcurrentHashMap<>(variables);
        this.methods = new ConcurrentHashMap<>(methods);
    }

    /**
     * Evaluates the code.
     * @param code The code. Each array entry represents one line of code.
     * @return The evaluation task.
     * @throws ZeusException If an error occurs.
     */
    @Override
    public ZeusTask evaluate(String[] code)
            throws ZeusException {
        if(code == null) {
            throw new ZeusException("The code provided must not be null.");
        }
        if(code.length == 0) {
            throw new ZeusException("The code provided must not be empty.");
        }
        ZeusTask task = new ZeusTaskImpl(this, code);
        task.evaluate();
        return task;
    }

    /**
     * Evaluates the code.
     * @param file The file containing the code.
     * @return The evaluation task.
     * @throws ZeusException If an error occurs.
     */
    @Override
    public ZeusTask evaluate(File file)
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
            return evaluate(lines.toArray(new String[lines.size()]));
        } catch(IOException exception) {
            throw new ZeusException(exception.getMessage());
        }
    }

    /**
     * Gets a variable by identifier.
     * @param identifier The identifier.
     * @return A variable, or null if a variable with that identifier does not exist.
     * @throws ZeusException If the identifier is null.
     */
    @Override
    public synchronized ZeusVariable getVariable(String identifier)
            throws ZeusException {
        if(identifier == null) {
            throw new ZeusException("The variable identifier may not be null.");
        }
        return variables.get(identifier);
    }

    /**
     * Gets a method by identifier.
     * @param identifier The identifier.
     * @return A method, or null if a method with that identifier does not exist.
     * @throws ZeusException If the identifier is null.
     */
    @Override
    public Method getMethod(String identifier)
            throws ZeusException {
        if(identifier == null) {
            throw new ZeusException("The method identifier may not be null.");
        }
        return methods.get(identifier);
    }

    /**
     * Updates a variable. This can be modifying its value
     * or simply creating it.
     * @param variable The variable.
     * @throws ZeusException If the variable is a constant.
     */
    @Override
    public synchronized void updateVariable(ZeusVariable variable)
            throws ZeusException {
        ZeusVariable existing = getVariable(variable.identifier());
        if(existing != null
                && existing.type() == VariableType.CONSTANT) {
            throw new ZeusException("You can not update a constant variable.");
        }

    }

    /**
     * Removes a method.
     * @param method The method to remove.
     * @throws ZeusException If the method cannot be removed.
     */
    @Override
    public synchronized void removeMethod(Method method)
            throws ZeusException {
        if(ZeusUtil.isStandardMethod(method)) {
            throw new ZeusException("Standard library methods may not be removed.");
        }
    }


}
