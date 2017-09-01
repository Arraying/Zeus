package de.arraying.zeus.runtime;

import de.arraying.zeus.backend.ZeusException;

import java.io.File;
import java.util.function.Consumer;

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
public interface ZeusRuntime {

    /**
     * Evaluates the code given.
     * @param code The code. Each array entry represents one line of code.
     * @param error The consumer for when an error occurs.
     * @return The Zeus evaluation task.
     * @throws ZeusException If an error occurs.
     */
    ZeusTask evaluate(String[] code, Consumer<ZeusException> error) throws ZeusException;

    /**
     * Evaluates the code given.
     * @param file The file containing the code.
     * @return The Zeus evaluation task.
     * @throws ZeusException If an error occurs.
     */
    ZeusTask evaluate(File file, Consumer<ZeusException> error) throws ZeusException;

    /**
     * Completely shuts down the runtime.
     * All current evaluations will at finish the line they are processing and then shut down.
     * @throws ZeusException If the runtime has already been shut down.
     */
    void shutdown() throws ZeusException;

}
