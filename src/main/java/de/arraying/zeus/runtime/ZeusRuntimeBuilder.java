package de.arraying.zeus.runtime;

import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.backend.ZeusMethods;
import de.arraying.zeus.backend.annotations.ZeusMethod;
import de.arraying.zeus.backend.annotations.ZeusStandard;
import de.arraying.zeus.event.ZeusEventListener;
import de.arraying.zeus.impl.ZeusRuntimeImpl;
import de.arraying.zeus.std.component.ZeusComponent;
import de.arraying.zeus.std.component.ZeusStandardComponent;
import de.arraying.zeus.std.component.components.MethodComponent;
import de.arraying.zeus.std.component.components.VariableComponent;
import de.arraying.zeus.std.component.components.condition.ElseComponent;
import de.arraying.zeus.std.component.components.condition.IfComponent;
import de.arraying.zeus.std.component.components.control.EndComponent;
import de.arraying.zeus.std.component.components.control.SleepComponent;
import de.arraying.zeus.std.component.components.control.StopComponent;
import de.arraying.zeus.std.method.ZeusStandardMethod;
import de.arraying.zeus.std.method.methods.*;
import de.arraying.zeus.variable.ZeusVariable;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
@SuppressWarnings("ALL")
public class ZeusRuntimeBuilder {

    private final Map<String, ZeusVariable> variables = new HashMap<>();
    private final Set<Method> methods = new HashSet<>();
    private final Map<Method, Object> methodContainers = new HashMap<>();
    private final Set<ZeusComponent> components = new HashSet<>();
    private final Set<ZeusEventListener> listeners = new HashSet<>();
    private final ZeusStandardMethod[] standardMethods = new ZeusStandardMethod[] {
            new ArithmeticMethods(),
            new ComparisonMethods(),
            new DatatypeMethods(),
            new OutputMethods(),
            new LogicMethods(),
            new StringMethods()
    };
    private final ZeusStandardComponent[] standardComponents = new ZeusStandardComponent[] {
            new ElseComponent(),
            new IfComponent(),
            new EndComponent(),
            new MethodComponent(),
            new SleepComponent(),
            new StopComponent(),
            new VariableComponent()
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
                    methods.add(method);
                    this.methodContainers.put(method, methodContainer);
                }
                continue;
            }
            for(Method method : provided.getMethods()) {
                if(!method.isAnnotationPresent(ZeusMethod.class)) {
                    continue;
                }
                if(!de.arraying.zeus.backend.ZeusMethod.isValidMethod(method)) {
                    throw new ZeusException("The provided method (" + method.getName() + ", " + method.getParameterCount() + " parameters) is invalid.");
                }
                methods.add(method);
                this.methodContainers.put(method, methodContainer);
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
     * Registers event listeners.
     * @param listeners A array of event listeners.
     * @return The builder.
     * @throws ZeusException If the event listeners are null.
     */
    public ZeusRuntimeBuilder withEventListeners(ZeusEventListener... listeners)
            throws ZeusException {
        for(ZeusEventListener listener : listeners) {
            if(listener == null) {
                throw new ZeusException("The provided event listener cannot be null.");
            }
            this.listeners.add(listener);
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
        if(timeoutThreshold < 2000) {
            throw new ZeusException("The provided timeout threshold must be at least 2000 milliseconds.");
        }
        if(timeoutThreshold > 43200000) {
            throw new ZeusException("The provided timeout threshold must be under 43200000 milliseconds.");
        }
        this.timeoutThreshold = timeoutThreshold;
        return this;
    }

    /**
     * Unregisters a method.
     * @param method The method.
     * @return The builder.
     * @throws ZeusException If the method is not registered.
     */
    public ZeusRuntimeBuilder withoutMethods(Method method)
            throws ZeusException {
        if(!methods.contains(method)) {
            throw new ZeusException("The provided method is not registered.");
        }
        methods.remove(components);
        return this;
    }

    /**
     * Unregisters a component.
     * @param component The component.
     * @return The builder.
     * @throws ZeusException If the component is not registered.
     */
    public ZeusRuntimeBuilder withoutComponent(ZeusComponent component)
            throws ZeusException {
        if(!components.contains(component)) {
            throw new ZeusException("The provided component is not registered.");
        }
        components.remove(component);
        return this;
    }

    /**
     * Builds the runtime.
     * @return A valid Zeus runtime.
     */
    public ZeusRuntime build() {
        return new ZeusRuntimeImpl(variables, new ZeusMethods(methods, methodContainers), components.toArray(new ZeusComponent[components.size()]),
                listeners.toArray(new ZeusEventListener[listeners.size()]), timeoutThreshold);
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
