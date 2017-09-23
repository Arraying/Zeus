package de.arraying.zeus.backend;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
public class ZeusMethods {

    /**
     * The index to use for variable argument methods.
     */
    static final int VARARGS_INDEX = -1;

    private final Map<String, Map<Integer, Method>> methods = new ConcurrentHashMap<>();

    /**
     * Creates a method collection object.
     * @param methods A set of all registered methods.
     */
    public ZeusMethods(Set<Method> methods) {
        for(Method method : methods) {
            registerMethod(method);
        }
    }

    /**
     * Gets a method.
     * @param name The identifier of the method.
     * @param parameterCount The parameter count.
     * @return A Method object if it exists, null otherwise.
     */
    public Method getMethod(String name, int parameterCount) {
        Map<Integer, Method> methods = this.methods.get(name);
        if(methods == null) {
            return null;
        }
        return methods.get(parameterCount);
    }

    /**
     * Registers the method.
     * @param method The method to register.
     */
    private void registerMethod(Method method) {
        String name = method.getName();
        if(methods.get(name) == null) {
            methods.put(name, new ConcurrentHashMap<>());
        }
        int params = method.isVarArgs() ? -1 : method.getParameterCount();
        methods.get(name).put(params, method);
    }

}
