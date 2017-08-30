package de.arraying.zeus.backend;

import de.arraying.zeus.impl.ZeusVariableImpl;
import de.arraying.zeus.variable.VariableType;
import de.arraying.zeus.variable.VariableValues;
import de.arraying.zeus.variable.ZeusVariable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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
public class ZeusUtil {

    /**
     * The author of Zeus.
     */
    public static final String AUTHOR = "Arraying";

    /**
     * The version of Zeus.
     */
    public static final String VERSION = "0.0.1";

    /**
     * Method to create a new variable.
     * @param type The variable type.
     * @param identifier The variable identifier.
     * @param value The variable value.
     * @return A valid ZeusVariable object.
     * @throws ZeusException If an exception occurs creating the variable.
     */
    public static ZeusVariable createVariable(VariableType type, String identifier, Object value)
            throws ZeusException {
        return new ZeusVariableImpl(type, identifier, value);
    }

    /**
     * Checks if an object is a supported variable value.
     * @param value The object value.
     * @return True if it is, false otherwise.
     */
    public static boolean isValidVariableValue(Object value) {
        return isValidValue(value.getClass());
    }

    /**
     * Checks if a method is valid.
     * @param method The method.
     * @return True if it is, false otherwise.
     */
    public static boolean isValidMethod(Method method) {
        if(method.isVarArgs()
                || !Modifier.isPublic(method.getModifiers())) {
            return false;
        }
        if(!method.getReturnType().equals(Void.TYPE)
                && !isValidValue(method.getReturnType().getClass())) {
            return false;
        }
        Class<?>[] parameters = method.getParameterTypes();
        if(parameters.length == 0) {
            return true;
        }
        for(Class parameter : parameters) {
            if(!isValidValue(parameter)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a method is a std library method.
     * @param method The method. This MUST be annotated with @ZeusMethod.
     * @return True if it is, false otherwise.
     */
    public static boolean isStandardMethod(Method method) {
        return method.getAnnotation(ZeusMethod.class).methodType() == ZeusMethod.Type.STD;
    }

    /**
     * Checks if the class given is a valid variable value class.
     * @param clazz The class.
     * @return True if it is, false otherwise.
     */
    private static boolean isValidValue(Class<?> clazz) {
        for(VariableValues value : VariableValues.values()) {
            if(value.getValueClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }

}
