package de.arraying.zeus.variable;

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
public enum VariableValues {

    /**
     * Represents an integer.
     * The range should be -2,147,483,648 to 2,147,483,647.
     */
    INT(Integer.class),

    /**
     * Represents a boolean, AKA a true/false value.
     * This should be true or false. Nothing else.
     */
    BOOL(Boolean.class),

    /**
     * Represents a long number, used to store numbers larger than an integer.
     * The range should be -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807.
     */
    LONG(Long.class),

    /**
     * Represents a decimal number. This value is 64bit and thus more accurate than a float.
     * The size can be a maximum fo 8 bytes, which approximates to around 15 decimal places.
     */
    DOUBLE(Double.class),

    /**
     * Represents a String. Unlike other programming languages, it is a primitive.
     * The value must be enclosed in quotation marks ("). Quotation marks may be escaped.
     */
    STRING(String.class);

    private final Class valueClass;

    /**
     * Sets the variable value class.
     * @param valueClass The class of the value.
     */
    VariableValues(Class valueClass) {
        this.valueClass = valueClass;
    }

    /**
     * Gets the variable value class.
     * @return A valid class.
     */
    public Class getValueClass() {
        return valueClass;
    }

}
