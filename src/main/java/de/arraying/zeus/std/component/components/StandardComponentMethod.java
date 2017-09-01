package de.arraying.zeus.std.component.components;

import de.arraying.zeus.backend.Patterns;
import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.backend.ZeusUtil;
import de.arraying.zeus.impl.ZeusTaskImpl;
import de.arraying.zeus.std.component.ZeusStandardComponent;
import de.arraying.zeus.token.Token;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
public class StandardComponentMethod implements ZeusStandardComponent {

    /**
     * Invokes the component.
     * @param runtime The impl of the task in order to access some impl only methods.
     * @param tokens An array of all tokens that have been tokenized.
     * @param lineNumber The line number.
     * @throws ZeusException if an error occurs.
     */
    @Override
    public void invoke(ZeusTaskImpl runtime, Token[] tokens, int lineNumber)
            throws ZeusException {
        Token identifier = tokens[0];
        if(identifier.getType() != Patterns.IDENTIFIER) {
            return;
        }
        if(!(tokens.length > 2
            && tokens[1].getType() == Patterns.TOKEN
            && tokens[1].getToken().equals("(")
            && tokens[tokens.length-1].getType() == Patterns.TOKEN
            && tokens[tokens.length-1].getToken().equals(")"))) {
            return;
        }
        Method method = runtime.getMethod(identifier.getToken());
        if(method == null) {
            throw new ZeusException("A method with the identifier \"" + identifier.getToken() + "\" does not exist at line " +
                    lineNumber + ".");
        }
        try {
            Object invocationObject = method.getDeclaringClass().newInstance();
            if(tokens.length != 3) {
                Object[] values = ZeusUtil.getMethodParameters(runtime, tokens, lineNumber);
                if(method.getParameterCount() != values.length) {
                    throw new ZeusException("Incorrect parameter count for the method invocation at line " + lineNumber + ".");
                }
                method.invoke(invocationObject, (Object[]) values);
            } else {
                method.invoke(invocationObject);
            }
        } catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
            throw new ZeusException("Encountered " + exception.toString());
        }
    }

}
