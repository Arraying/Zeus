package de.arraying.zeus.backend;

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
public class ZeusMethod {

    private final Object returnValue;
    private final boolean valid;

    /**
     * Creates a new Zeus method result.
     * @param returnValue The return value. Can be null.
     * @param valid Whether or not the method is valid.
     */
    public ZeusMethod(Object returnValue, boolean valid) {
        this.valid = valid;
        this.returnValue = returnValue;
    }

    /**
     * Gets the return value.
     * @return The return value.
     */
    public Object getReturnValue() {
        return returnValue;
    }

    /**
     * Whether the method is valid.
     * @return The validity.
     */
    public boolean isValid() {
        return valid;
    }

}
