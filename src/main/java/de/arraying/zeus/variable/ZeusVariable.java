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
public interface ZeusVariable {

    /**
     * Gets the variable type.
     * @return The variable type.
     */
    VariableType type();

    /**
     * Gets the variable identifier.
     * This identifier should be a valid identifier matching the identifier pattern.
     * @return The variable identifier.
     */
    String identifier();

    /**
     * Gets the variable value.
     * This value should be one of the values defined in VariableValues.
     * @return The variable value.
     */
    Object value();

}
