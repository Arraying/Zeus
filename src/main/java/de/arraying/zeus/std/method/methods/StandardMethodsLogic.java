package de.arraying.zeus.std.method.methods;

import de.arraying.zeus.backend.annotations.ZeusStandard;
import de.arraying.zeus.std.method.ZeusStandardMethod;

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
public @ZeusStandard class StandardMethodsLogic implements ZeusStandardMethod {

    /**
     * The and logical operator.
     * @param one The first boolean.
     * @param two The second boolean.
     * @return The result.
     */
    public boolean and(boolean one, boolean two) {
        return one && two;
    }

    /**
     * The or logical operator.
     * @param one The first boolean.
     * @param two The second boolean.
     * @return The result.
     */
    public boolean or(boolean one, boolean two) {
        return one || two;
    }


    /**
     * The not logical operator.
     * @param input The input.
     * @return The result.
     */
    public boolean not(boolean input) {
        return !input;
    }

}
