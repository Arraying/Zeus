package de.arraying.zeus.runtime;

import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.backend.ZeusUtil;
import de.arraying.zeus.backend.annotations.ZeusMethod;
import de.arraying.zeus.backend.annotations.ZeusStandard;
import de.arraying.zeus.impl.ZeusRuntimeImpl;
import de.arraying.zeus.std.component.ZeusComponent;
import de.arraying.zeus.std.component.ZeusStandardComponent;
import de.arraying.zeus.std.component.components.StandardComponentMethod;
import de.arraying.zeus.std.component.components.StandardComponentVariable;
import de.arraying.zeus.std.method.ZeusStandardMethod;
import de.arraying.zeus.std.method.methods.StandardMethodsOut;
import de.arraying.zeus.variable.ZeusVariable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final Map<String, ZeusVariable> variables = new HashMap<>();
    private final Map<String, Method> methods = new HashMap<>();
    private final List<ZeusComponent> components = new ArrayList<>();
    private final ZeusStandardMethod[] standardMethods = new ZeusStandardMethod[] {
            new StandardMethodsOut()
    };
    private final ZeusStandardComponent[] standardComponents = new ZeusStandardComponent[] {
            new StandardComponentVariable(),
            new StandardComponentMethod()
    };
    private int timeoutThreshold = -1;

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
            if(provided.isAnnotationPresent(ZeusStandard.class)) {
                for(Method method : provided.getMethods()) {
                    methods.put(method.getName(), method);
                }
                return this;
            }
            if(!ZeusUtil.isValidMethodContainer(provided)) {
                throw new ZeusException("The method container must have an empty constructor and must be accessible.");
            }
            for(Method method : provided.getMethods()) {
                if(!method.isAnnotationPresent(ZeusMethod.class)) {
                    continue;
                }
                if(!ZeusUtil.isValidMethod(method)) {
                    throw new ZeusException("The provided method (" + method.getName() + ") is invalid.");
                }
                methods.put(method.getName(), method);
            }
        }
        return this;
    }

    /**
     * Registers pre-created variables.
     * @param variables An array of variables.
     * @return The builder.
     * @throws ZeusException If the variables are null.
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
     * Registers components.
     * @param components An array of components.
     * @return The builder.
     * @throws ZeusException If the components are null.
     */
    public ZeusRuntimeBuilder withComponents(ZeusComponent... components)
            throws ZeusException {
        for(ZeusComponent component : components) {
            if(component == null) {
                throw new ZeusException("The provided component cannot be null");
            }
            this.components.add(component);
        }
        return this;
    }

    /**
     * Sets the maximum runtime for a task, in order to counter infinity loops and such.
     * @param timeoutThreshold The time in milliseconds. Must be greater than 2000 and 43200000
     * @return The builder.
     * @throws ZeusException If the provided timeout threshold if out of the supported rage.
     */
    public ZeusRuntimeBuilder withMaximumRuntime(int timeoutThreshold)
            throws ZeusException {
//        if(timeoutThreshold < 2000) {
//            throw new ZeusException("The provided timeout threshold must be at least 2000 milliseconds.");
//        }
        if(timeoutThreshold > 43200000) {
            throw new ZeusException("The provided timeout threshold must be under 43200000 milliseconds.");
        }
        this.timeoutThreshold = timeoutThreshold;
        return this;
    }

    /**
     * Builds the runtime.
     * @return A valid Zeus runtime.
     */
    public ZeusRuntime build() {
        return new ZeusRuntimeImpl(variables, methods, components.toArray(new ZeusComponent[components.size()]), timeoutThreshold);
    }

    /**
     * Registers the standard library, also referred to as "std".
     * This includes both components and methods.
     * @throws ZeusException If an error occurs.
     */
    private void registerStandard()
            throws ZeusException {
        withMethods((Object[]) standardMethods);
        withComponents((ZeusComponent[]) standardComponents);
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
