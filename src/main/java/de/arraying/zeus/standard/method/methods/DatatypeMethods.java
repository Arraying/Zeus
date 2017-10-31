package de.arraying.zeus.standard.method.methods;

import de.arraying.zeus.backend.annotations.ZeusStandard;
import de.arraying.zeus.standard.method.ZeusStandardMethod;

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
public @ZeusStandard class DatatypeMethods implements ZeusStandardMethod {

    /**
     * Whether or not an object is a boolean.
     * @param object The object.
     * @return True if it is, false otherwise.
     */
    public Boolean is_bool(Object object) {
        return object instanceof Boolean;
    }

    /**
     * Whether or not an object is a long.
     * @param object The object.
     * @return True if it is, false otherwise.
     */
    public Boolean is_long(Object object) {
        return object instanceof Long;
    }

    /**
     * Whether or not an object is a double.
     * @param object The object.
     * @return True if it is, false otherwise.
     */
    public Boolean is_double(Object object) {
        return object instanceof Double;
    }

    /**
     * Whether or not an object is an int.
     * @param object The object.
     * @return True if it is, false otherwise.
     */
    public Boolean is_int(Object object) {
        return object instanceof Integer;
    }

    /**
     * Whether or not an object is a string.
     * @param object The object.
     * @return True if it is, false otherwise.
     */
    public Boolean is_str(Object object) {
        return object instanceof String;
    }

    /**
     * Converts an object to a boolean.
     * Could cause errors.
     * @param object The object.
     * @return A boolean.
     */
    public Boolean to_boo(Object object) {
        return Boolean.valueOf(object.toString());
    }

    /**
     * Converts an object to a long.
     * Could cause errors.
     * @param object The object.
     * @return A long.
     */
    public Long to_long(Object object) {
        return Long.valueOf(object.toString());
    }

    /**
     * Converts an object to a double.
     * Could cause errors.
     * @param object The object.
     * @return A double.
     */
    public Double to_double(Object object) {
        return Double.valueOf(object.toString());
    }

    /**
     * Converts an object to an int.
     * Could cause errors.
     * @param object The object.
     * @return An int.
     */
    public Integer to_int(Object object) {
        return Integer.valueOf(object.toString());
    }

    /**
     * Converts an object to a string.
     * @param object The object.
     * @return A string.
     */
    public String to_str(Object object) {
        return object.toString();
    }

    /**
     * Gets the length of an object.
     * @param object The object.
     * @return The length as an integer.
     */
    public Integer len(Object object) {
        return object.toString().length();
    }

}
