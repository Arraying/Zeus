package de.arraying.zeus;

import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.backend.ZeusUtil;
import de.arraying.zeus.backend.annotations.ZeusMethod;
import de.arraying.zeus.runtime.ZeusRuntime;
import de.arraying.zeus.runtime.ZeusRuntimeBuilder;
import de.arraying.zeus.runtime.ZeusTask;
import de.arraying.zeus.variable.VariableType;

import java.io.File;

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
public class Example {

    /**
     * Prints my variable's value!
     * The method name is in the Zeus' recommended convention.
     * @param variableValue The value of my variable.
     */
    @ZeusMethod
    public void show_my_variable(String variableValue) {
        System.out.println("My variable's value is: " + variableValue + "!");
    }

    /**
     * The main method of the program.
     * @param args The startup arguments.
     */
    public static void main(String[] args) {
        try {
            // First, you have to create a runtime. This can be done using the runtime builder.
            // The Configuration enumeration is used to choose what kind of configuration you want.
            // STANDARD -> Will register everything you need for you, including stdlib (standard library).
            // CUSTOM -> You will have to register everything on your own.
            ZeusRuntimeBuilder builder = new ZeusRuntimeBuilder(ZeusRuntimeBuilder.Configuration.STANDARD);

            // Now you need to register the method you have created.
            // You can do this by calling .withMethods and an instance of the method container.
            // .withMethods supports varargs, so you can register as many method containers as you want.
            // All builder methods return an instance of the builder, so you can just "stack" the methods
            // and you don't have to call builder.x every time.
            builder.withMethods(new Example());

            // You can also predefine variables before runtime.
            // You need a ZeusVariable object for this. The easiest way to obtain one is using ZeusUtil.
            // Like .withMethods, .withVariables also supports varargs.
            builder.withVariables(ZeusUtil.createVariable(VariableType.CONSTANT, "my_var", "My Variable"));

            // There are a lot of other methods that can be used in the builder, but for the sake of keeping
            // this simple they will not be covered.
            // Now it's time to create the actual runtime.
            ZeusRuntime runtime = builder.build();

            // Now you can evaluate code. Evaluating will return a ZeusTask.
            // A ZeusTask is an evaluation task that will run async.
            // There are two evaluate parameters: Either a String[] of lines or a file and a consumer.
            // This consumer is the error consumer. The consumer handles all errors due to the fact that the
            // tasks are performed in async. The consumer type is ZeusException. In this example, the error will
            // be printed to the console, but it's essentially up to you.
            // Should you not want an error handler, you can set the parameter to null.
            // See the example.zeus file for the actual code.
            ZeusTask task = runtime.evaluate(new File("example.zeus"), Throwable::printStackTrace);

        } catch(ZeusException exception) { // Exceptions can occur during the building process, too.
            exception.printStackTrace();
        }
    }

}
