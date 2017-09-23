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
@ZeusStandard
@SuppressWarnings({"unused", "WeakerAccess"})
public class StandardMethodsArithmetic implements ZeusStandardMethod {

    /**
     * Alias of the add() method.
     */
    public Integer add_i(Object... entries) {
        return add(entries);
    }

    /**
     * Alias of the subtract() method.
     */
    public Integer subtract_i(Object... entries) {
        return subtract(entries);
    }

    /**
     * Alias of the multiply() method.
     */
    public Integer multiply_i(Object... entries) {
        return multiply(entries);
    }

    /**
     * Alias of the divide() method.
     */
    public Integer divide_i(Object... entries) {
        return divide(entries);
    }

    /**
     * Alias of the modulo(a, b) method.
     */
    public Integer modulo_i(Integer a, Integer b) {
        return modulo(a, b);
    }

    /**
     * Adds integers.
     * @param entries An array of numbers.
     * @return Added numbers, or 0 if none were specified.
     * @throws IllegalArgumentException If not all objects in the array are Integers.
     */
    public Integer add(Object... entries) {
        int i = 0;
        if(entries.length == 0) {
            return i;
        }
        for(Object entry : entries) {
            if(!(entry instanceof Integer)) {
                throw new IllegalArgumentException("Expected type integer while adding, found \"" + entry + "\".");
            }
            i += (Integer) entry;
        }
        return i;
    }

    /**
     * Subtracts integers.
     * @param entries An array of numbers.
     * @return Subtracted numbers, or 0 if none were specified.
     * @throws IllegalArgumentException If not all objects in the array are Integers.
     */
    public Integer subtract(Object... entries) {
        int i = 0;
        if(entries.length == 0) {
            return i;
        }
        for(Object entry : entries) {
            if(!(entry instanceof Integer)) {
                throw new IllegalArgumentException("Expected type integer while adding, found \"" + entry + "\".");
            }
            i -= (Integer) entry;
        }
        return i;
    }

    /**
     * Multiplies integers.
     * @param entries An array of numbers.
     * @return Multiplied numbers, or 1 if none were specified.
     * @throws IllegalArgumentException If not all objects in the array are Integers.
     */
    public Integer multiply(Object... entries) {
        int i = 1;
        if(entries.length == 0) {
            return i;
        }
        for(Object entry : entries) {
            if(!(entry instanceof Integer)) {
                throw new IllegalArgumentException("Expected type integer while adding, found \"" + entry + "\".");
            }
            i *= (Integer) entry;
        }
        return i;
    }

    /**
     * Divides integers.
     * @param entries An array of numbers.
     * @return Divided numbers, or 1 if none were specified.
     * @throws IllegalArgumentException If not all objects in the array are Integers.
     */
    public Integer divide(Object... entries) {
        int i = 1;
        if(entries.length == 0) {
            return i;
        }
        for(Object entry : entries) {
            if(!(entry instanceof Integer)) {
                throw new IllegalArgumentException("Expected type integer while adding, found \"" + entry + "\".");
            }
            i *= (Integer) entry;
        }
        return i;
    }

    /**
     * Works out the remainder of the division of two numbers.
     * @param a The first number
     * @param b The second number.
     * @return The remainder.
     */
    public Integer modulo(Integer a, Integer b) {
        return a % b;
    }

    /**
     * Adds longs.
     * @param entries An array of numbers.
     * @return Added numbers, or 0 if none were specified.
     * @throws IllegalArgumentException If not all objects in the array are Longs.
     */
    public Long add_l(Object... entries) {
        Long i = 0L;
        if(entries.length == 0) {
            return i;
        }
        for(Object entry : entries) {
            if(!(entry instanceof Long)) {
                throw new IllegalArgumentException("Expected type Long while adding, found \"" + entry + "\".");
            }
            i += (Long) entry;
        }
        return i;
    }

    /**
     * Subtracts longs.
     * @param entries An array of numbers.
     * @return Subtracted numbers, or 0 if none were specified.
     * @throws IllegalArgumentException If not all objects in the array are Longs.
     */
    public Long subtract_l(Object... entries) {
        Long i = 0L;
        if(entries.length == 0) {
            return i;
        }
        for(Object entry : entries) {
            if(!(entry instanceof Long)) {
                throw new IllegalArgumentException("Expected type Long while adding, found \"" + entry + "\".");
            }
            i -= (Long) entry;
        }
        return i;
    }

    /**
     * Multiplies longs.
     * @param entries An array of numbers.
     * @return Multiplied numbers, or 1 if none were specified.
     * @throws IllegalArgumentException If not all objects in the array are Longs.
     */
    public Long multiply_l(Object... entries) {
        Long i = 1L;
        if(entries.length == 0) {
            return i;
        }
        for(Object entry : entries) {
            if(!(entry instanceof Long)) {
                throw new IllegalArgumentException("Expected type Long while adding, found \"" + entry + "\".");
            }
            i *= (Long) entry;
        }
        return i;
    }

    /**
     * Divides longs.
     * @param entries An array of numbers.
     * @return Divided numbers, or 1 if none were specified.
     * @throws IllegalArgumentException If not all objects in the array are Longs.
     */
    public Long divide_l(Object... entries) {
        Long i = 1L;
        if(entries.length == 0) {
            return i;
        }
        for(Object entry : entries) {
            if(!(entry instanceof Long)) {
                throw new IllegalArgumentException("Expected type Long while adding, found \"" + entry + "\".");
            }
            i *= (Long) entry;
        }
        return i;
    }

    /**
     * Works out the remainder of the division of two numbers.
     * @param a The first number
     * @param b The second number.
     * @return The remainder.
     */
    public Long modulo_l(Long a, Long b) {
        return a % b;
    }

    /**
     * Adds doubles.
     * @param entries An array of numbers.
     * @return Added numbers, or 0 if none were specified.
     * @throws IllegalArgumentException If not all objects in the array are Doubles.
     */
    public Double add_d(Object... entries) {
        Double i = 0D;
        if(entries.length == 0) {
            return i;
        }
        for(Object entry : entries) {
            if(!(entry instanceof Double)) {
                throw new IllegalArgumentException("Expected type Double while adding, found \"" + entry + "\".");
            }
            i += (Double) entry;
        }
        return i;
    }

    /**
     * Subtracts doubles.
     * @param entries An array of numbers.
     * @return Subtracted numbers, or 0 if none were specified.
     * @throws IllegalArgumentException If not all objects in the array are Doubles.
     */
    public Double subtract_d(Object... entries) {
        Double i = 0D;
        if(entries.length == 0) {
            return i;
        }
        for(Object entry : entries) {
            if(!(entry instanceof Double)) {
                throw new IllegalArgumentException("Expected type Double while adding, found \"" + entry + "\".");
            }
            i -= (Double) entry;
        }
        return i;
    }

    /**
     * Multiplies doubles.
     * @param entries An array of numbers.
     * @return Multiplied numbers, or 1 if none were specified.
     * @throws IllegalArgumentException If not all objects in the array are Doubles.
     */
    public Double multiply_d(Object... entries) {
        Double i = 1D;
        if(entries.length == 0) {
            return i;
        }
        for(Object entry : entries) {
            if(!(entry instanceof Double)) {
                throw new IllegalArgumentException("Expected type Double while adding, found \"" + entry + "\".");
            }
            i *= (Double) entry;
        }
        return i;
    }

    /**
     * Divides doubles.
     * @param entries An array of numbers.
     * @return Divided numbers, or 1 if none were specified.
     * @throws IllegalArgumentException If not all objects in the array are Doubles.
     */
    public Double divide_d(Object... entries) {
        Double i = 1D;
        if(entries.length == 0) {
            return i;
        }
        for(Object entry : entries) {
            if(!(entry instanceof Double)) {
                throw new IllegalArgumentException("Expected type Double while adding, found \"" + entry + "\".");
            }
            i *= (Double) entry;
        }
        return i;
    }

    /**
     * Works out the remainder of the division of two numbers.
     * @param a The first number
     * @param b The second number.
     * @return The remainder.
     */
    public Double modulo_d(Double a, Double b) {
        return a % b;
    }
    
}
