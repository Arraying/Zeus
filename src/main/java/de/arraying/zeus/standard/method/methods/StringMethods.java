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
@ZeusStandard
@SuppressWarnings("unused")
public class StringMethods implements ZeusStandardMethod {

    /**
     * Concatenates a string using a StringBuilder.
     * @param entries An array of entries to concatenate.
     * @return A concatenated string.
     */
    public String concat(Object... entries) {
        StringBuilder builder = new StringBuilder();
        for(Object entry : entries) {
            builder.append(entry);
        }
        return builder.toString();
    }

    /**
     * Appends a string to the provided string.
     * @param value The provided string.
     * @param append The string to append.
     * @return An appended string.
     */
    public String str_concat(String value, String append) {
        return value.concat(append);
    }

    /**
     * Checks whether the provided string contains a character sequence.
     * @param value The provided string.
     * @param content The character sequence.
     * @return True if it does, false otherwise.
     */
    public boolean str_contains(String value, String content) {
        return value.contains(content);
    }

    /**
     * Checks whether the provided string ends with another string.
     * @param value The provided string.
     * @param suffix The suffix.
     * @return True if it does, false otherwise.
     */
    public boolean str_ends(String value, String suffix) {
        return value.endsWith(suffix);
    }

    /**
     * Checks if a string equals to another string. Ignores cases.
     * @param value The first string.
     * @param anotherString The second string.
     * @return True if they are equal, false otherwise.
     */
    public boolean str_equals_ignore_case(String value, String anotherString) {
        return value.equalsIgnoreCase(anotherString);
    }

    /**
     * Gets the index of a character sequence in a provided string.
     * @param value The provided string.
     * @param wantedIndex The character sequence.
     * @return The index (0 based), or -1 if it could not be found.
     */
    public int str_index(String value, String wantedIndex) {
        return value.indexOf(wantedIndex);
    }

    /**
     * Gets the index of a character sequence in a provided string.
     * @param value The provided string.
     * @param wantedIndex The character sequence.
     * @param startingIndex The index to start looking at.
     * @return The index (0 based), or -1 if it could not be found.
     */
    public int str_index(String value, String wantedIndex, int startingIndex) {
        return value.indexOf(wantedIndex, startingIndex);
    }

    /**
     * Checks whether the provided string is empty (length == 0).
     * @param value The provided string.
     * @return True if it is, false otherwise.
     */
    public boolean str_is_empty(String value) {
        return value.isEmpty();
    }

    /**
     * Gets the last index of a character sequence in a provided string.
     * @param value The provided string.
     * @param wantedIndex The character sequence.
     * @return The index (0 based), or -1 if it could not be found.
     */
    public int str_lindex(String value, String wantedIndex) {
        return value.lastIndexOf(wantedIndex);
    }

    /**
     * Gets the last index of a character sequence in a provided string.
     * @param value The provided string.
     * @param wantedIndex The character sequence.
     * @param startingIndex The index to start looking at.
     * @return The index (0 based), or -1 if it could not be found.
     */
    public int str_lindex(String value, String wantedIndex, int startingIndex) {
        return value.lastIndexOf(wantedIndex, startingIndex);
    }

    /**
     * Gets the length of a provided string.
     * @param value The provided string.
     * @return The length.
     */
    public int str_len(String value) {
        return value.length();
    }

    /**
     * Replaces a character sequence with another one in a provided string.
     * @param value The provided string.
     * @param from The character sequence to replace.
     * @param to The character sequence to use as a replacement.
     * @return A replaced string.
     */
    public String str_replace(String value, String from, String to) {
        return value.replace(from, to);
    }

    /**
     * Whether or not a provided string starts with the specified prefix.
     * @param value The provided string.
     * @param prefix The specified prefix.
     * @return True if it does, false otherwise.
     */
    public boolean str_starts(String value, String prefix) {
        return value.startsWith(prefix);
    }

    /**
     * Whether or not a provided string starts with the specified prefix.
     * @param value The provided string.
     * @param prefix The specified prefix.
     * @param startingPoint The starting index.
     * @return True if it does, false otherwise.
     */
    public boolean str_starts(String value, String prefix, int startingPoint) {
        return value.startsWith(prefix, startingPoint);
    }

    /**
     * Substrings a provided string.
     * @param value The provided string.
     * @param index The starting index to begin substringing.
     * @return A substring.
     */
    public String str_substr(String value, int index) {
        return value.substring(index);
    }

    /**
     * Substrings a provided string.
     * @param value The provided string.
     * @param index The starting index to begin substringing.
     * @param end The ending index to stop substringing.
     * @return A substring.
     */
    public String str_substr(String value, int index, int end) {
        return value.substring(index, end);
    }

    /**
     * Converts a provided string to lowercase.
     * @param value The provided string.
     * @return A lowercase string.
     */
    public String str_lower(String value) {
        return value.toLowerCase();
    }

    /**
     * Converts a provided string to uppercase.
     * @param value The provided string.
     * @return An uppercase string.
     */
    public String str_upper(String value) {
        return value.toUpperCase();
    }

    /**
     * Trims a provided string, removes any leading or trailing whitespace.
     * @param value The provided string.
     * @return A trimmed string.
     */
    public String str_trim(String value) {
        return value.trim();
    }

}
