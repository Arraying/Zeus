package de.arraying.zeus.token;

import de.arraying.zeus.backend.Patterns;

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
public class Token {

    private final String token;
    private final Patterns type;

    /**
     * Creates a new token.
     * @param token The token, literally.
     * @param type The pattern object containing type + regex.
     */
    Token(String token, Patterns type) {
        this.token = token;
        this.type = type;
    }

    /**
     * Gets the token.
     * @return The string token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the token type.
     * @return The type.
     */
    public Patterns getType() {
        return type;
    }

}
