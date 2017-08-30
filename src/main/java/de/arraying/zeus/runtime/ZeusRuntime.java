package de.arraying.zeus.runtime;

import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.variable.ZeusVariable;

import java.io.File;
import java.lang.reflect.Method;

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
public interface ZeusRuntime {

    /**
     * Evaluates the code given.
     * @param code The code. Each array entry represents one line of code.
     * @return The Zeus evaluation task.
     * @throws ZeusException If an error occurs.
     */
    ZeusTask evaluate(String[] code) throws ZeusException;

    /**
     * Evaluates the code given.
     * @param file The file containing the code.
     * @return The Zeus evaluation task.
     * @throws ZeusException If an error occurs.
     */
    ZeusTask evaluate(File file) throws ZeusException;

    /**
     * Gets a variable by identifier.
     * Must run synchronized.
     * @param identifier The identifier.
     * @return A variable or null.
     * @throws ZeusException If the identifier is null.
     */
    ZeusVariable getVariable(String identifier) throws ZeusException;

    /**
     * Gets a method by identifier.
     * Must be run synchronised.
     * @param identifier The identifier.
     * @return A method or null.
     * @throws ZeusException If the identifier is null.
     */
    Method getMethod(String identifier) throws ZeusException;

    /**
     * Updates a variable.
     * Must run synchronized.
     * @param variable The variable.
     * @throws ZeusException If the variable is immutable.
     */
    void updateVariable(ZeusVariable variable) throws ZeusException;

    /**
     * Removes a method.
     * Must run synchronized.
     * @param method The method to remove.
     * @throws ZeusException If the method is a std method.
     */
    void removeMethod(Method method) throws ZeusException;

}
