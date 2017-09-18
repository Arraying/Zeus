package de.arraying.zeus.token;

import de.arraying.zeus.backend.Patterns;
import de.arraying.zeus.backend.ZeusException;

import java.util.LinkedList;
import java.util.regex.Matcher;

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
public class Tokenizer {

    private final int lineNumber;
    private final LinkedList<TokenEntry> entries;
    private String line;
    private Token[] cachedTokens;

    /**
     * Creates a new tokenizer.
     * @param line The line to tokenize.
     * @param lineNumber The line number.
     */
    public Tokenizer(String line, int lineNumber) {
        this.lineNumber = lineNumber;
        this.entries = new LinkedList<>();
        this.line = line
                .replaceAll(Patterns.TI_COMMENT_SINGLE.getStringPattern(), "");
        for(Patterns pattern : Patterns.values()) {
            if(pattern.name().startsWith("TI")) {
                continue;
            }
            TokenEntry entry = new TokenEntry(pattern);
            entries.add(entry);
        }
    }

    /**
     * Gets all the tokens of the current line.
     * @return An array of tokens.
     * @throws ZeusException If an error occurs.
     */
    public Token[] getTokens()
            throws ZeusException {
        if(cachedTokens != null) {
            return cachedTokens;
        }
        LinkedList<Token> tokens = new LinkedList<>();
        whileLoop:
        while(!line.isEmpty()) {
            line = line.trim();
            for(TokenEntry entry : entries) {
                Matcher matcher = entry.getPattern().matcher(line);
                if(!matcher.find()) {
                    continue;
                }
                String tokenString = matcher.group().trim();
                tokens.add(new Token(tokenString, entry.getType()));
                line = matcher.replaceFirst("");
                continue whileLoop;
            }
            throw new ZeusException("Unknown token \"" + line + "\".", lineNumber);
        }
        cachedTokens = tokens.toArray(new Token[tokens.size()]);
        return cachedTokens;
    }

}
