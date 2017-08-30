package de.arraying.zeus.runtime;

import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.backend.ZeusMethod;
import de.arraying.zeus.backend.ZeusUtil;
import de.arraying.zeus.impl.ZeusRuntimeImpl;
import de.arraying.zeus.std.Out;
import de.arraying.zeus.std.Standard;
import de.arraying.zeus.variable.ZeusVariable;

import java.lang.reflect.Method;
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
@SuppressWarnings("unused")
public class ZeusRuntimeBuilder {

    private final ConcurrentMap<String, ZeusVariable> variables = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Method> methods = new ConcurrentHashMap<>();
    private final Standard[] standards = new Standard[] {
            new Out()
    };

    /**
     * Creates a new runtime builder.
     * @param configuration The configuration to use.
     * @throws ZeusException If an error occurs.
     */
    public ZeusRuntimeBuilder(Configuration configuration)
            throws ZeusException {
        if(configuration == null) {
            throw new ZeusException("The provided configuration cannot be null.");
        }
        if(configuration != Configuration.CUSTOM) {
            registerStandard();
        }
    }

    /**
     * Registers methods annotated with the ZeusMethod annotation.
     * @param methodContainers An array of objects.
     * @return The builder.
     * @throws ZeusException If an error occurs.
     */
    public ZeusRuntimeBuilder withMethods(Object... methodContainers)
            throws ZeusException {
        for(Object methodContainer : methodContainers) {
            if(methodContainer == null) {
                throw new ZeusException("The provided method container cannot be null.");
            }
            Class provided = methodContainer.getClass();
            for(Method method : provided.getMethods()) {
                if(method.isAnnotationPresent(ZeusMethod.class)) {
                    if(!ZeusUtil.isValidMethod(method)) {
                        throw new ZeusException("The provided method is invalid.");
                    }
                    methods.put(method.getName(), method);
                }
            }
        }
        return this;
    }

    /**
     * Registers pre-created variables.
     * @param variables An array of variables.
     * @return The builder.
     * @throws ZeusException If an error occurs.
     */
    public ZeusRuntimeBuilder withVariables(ZeusVariable... variables)
            throws ZeusException {
        for(ZeusVariable variable : variables) {
            if(variable == null) {
                throw new ZeusException("The provided variable cannot be null.");
            }
            this.variables.put(variable.identifier(), variable);
        }
        return this;
    }

    /**
     * Builds the runtime.
     * @return A valid Zeus runtime.
     */
    public ZeusRuntime build() {
        return new ZeusRuntimeImpl(variables, methods);
    }

    /**
     * Registers the standard library, also referred to as "std".
     * @throws ZeusException If an error occurs.
     */
    private void registerStandard()
            throws ZeusException {
        withMethods((Object[]) standards);
    }

    public enum Configuration {

        /**
         * The standard configuration.
         * Registers all standard components.
         */
        STANDARD,

        /**
         * A custom configuration.
         * Requires registration for all wanted components.
         */
        CUSTOM

    }

}
