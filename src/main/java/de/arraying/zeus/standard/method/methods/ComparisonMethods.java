package de.arraying.zeus.standard.method.methods;

import de.arraying.zeus.backend.annotations.ZeusStandard;
import de.arraying.zeus.standard.method.ZeusStandardMethod;
import de.arraying.zeus.utils.ZeusUtil;

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
public @ZeusStandard class ComparisonMethods implements ZeusStandardMethod {

    /**
     * The equals comparison.
     * @param a The first value.
     * @param b The second value.
     * @return Whether or not the first value is equal to the second value.
     */
    public Boolean equals(Object a, Object b) {
        return a.equals(b);
    }

    /**
     * Whether or not the first number is less than the second number.
     * @param a The first number.
     * @param b The second number.
     * @return True if it is, false otherwise.
     */
    public Boolean less(Number a, Number b) {
        return ZeusUtil.compare(a, b, ZeusUtil.COMP_LESS);
    }

    /**
     * Whether or not the first number is less than or equal to the second number.
     * @param a The first number.
     * @param b The second number.
     * @return True if it is, false otherwise.
     */
    public Boolean less_or_equal(Number a, Number b) {
        return ZeusUtil.compare(a, b, ZeusUtil.COMP_LESS_OR_EQUAL);
    }

    /**
     * Whether or not the first number is more than the second number.
     * @param a The first number.
     * @param b The second number.
     * @return True if it is, false otherwise.
     */
    public Boolean more_or_equal(Number a, Number b) {
        return ZeusUtil.compare(a, b, ZeusUtil.COMP_MORE_OR_EQUAL);
    }

    /**
     * Whether or not the first number is more than or equal to the second number.
     * @param a The first number.
     * @param b The second number.
     * @return True if it is, false otherwise.
     */
    public Boolean more(Number a, Number b) {
        return ZeusUtil.compare(a, b, ZeusUtil.COMP_MORE);
    }

}
