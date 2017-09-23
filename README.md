![Zeus](http://i.imgur.com/7kZbvbW.png)

# A custom scripting language

[![Zeus](https://jitpack.io/v/Arraying/life.svg)](https://jitpack.io/#arraying/zeus)

Zeus is a custom scripting language designed to be embedded into Java code to allow users to execute scripts in an enviroment that is fully manageable by the developer(s).

# Usage

Simply add the library as a dependency. 
*Replace {version} with the version you would like, a list can be found [here](https://github.com/Arraying/Zeus/releases).*

### Maven

```xml
  <repositories>
    <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
    </repository>
  </repositories>
  <dependencies>
    <dependency>
      <groupId>com.github.Arraying</groupId>
      <artifactId>Zeus</artifactId>
      <version>{version}</version>
    </dependency>
  </dependencies>
```

### Gradle

```gradle
  repositories {
    maven { 
      url 'https://jitpack.io' 
    }
  }
  dependencies {
    compile 'com.github.Arraying:Zeus:{version}'
  }
```

### Code

It's fairly forward creating the things inside of java.
The documented version of the example can be found [here](https://github.com/Arraying/Zeus/blob/master/src/main/java/de/arraying/zeus/Example.java).
The file mentioned, example.zeus, can be found [here](https://github.com/Arraying/Zeus/blob/master/example.zeus).
```java
package de.arraying.zeus;

import de.arraying.zeus.backend.ZeusException;
import de.arraying.zeus.backend.annotations.ZeusMethod;
import de.arraying.zeus.runtime.ZeusRuntime;
import de.arraying.zeus.runtime.ZeusRuntimeBuilder;
import de.arraying.zeus.runtime.ZeusTask;
import de.arraying.zeus.utils.ZeusVariableUtil;
import de.arraying.zeus.variable.VariableType;

import java.io.File;

public class Example {

    @ZeusMethod
    public void show_my_variable(String variableValue) {
        System.out.println("My variable's value is: " + variableValue + "!");
    }

    public static void main(String[] args) {
        try {
            ZeusRuntimeBuilder builder = new ZeusRuntimeBuilder(ZeusRuntimeBuilder.Configuration.STANDARD);
            builder.withMethods(new Example());
            builder.withVariables(ZeusVariableUtil.createVariable(VariableType.CONSTANT, "my_var", "My Variable"));
            ZeusRuntime runtime = builder.build();
            ZeusTask task = runtime.evaluate(new File("example.zeus"), Throwable::printStackTrace);
        } catch(ZeusException exception) { // Exceptions can occur during the building process, too.
            exception.printStackTrace();
        }
    }

}

```

# Information

For information concering the actual language please [visit the wiki](https://github.com/Arraying/Zeus/wiki).
All methods are documented, a JavaDoc is coming soon.

# Development

Zeus is still in heavy development and no where near complete. Should you fix any bugs or add features please make a pull request.




