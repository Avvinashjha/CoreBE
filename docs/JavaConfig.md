To use **Java-based configuration** in Spring to manage beans, you define a configuration class (e.g., `BeanConfig`) and manage beans programmatically by creating and registering them within the class. This approach eliminates the need for XML configuration files. The configuration class is annotated with `@Configuration`, and individual beans are created using the `@Bean` annotation. Dependency injection is handled via method calls or annotations.

Below is an example of how to achieve this:

---

## Example: Managing Beans Using `BeanConfig` Class

### Scenario:
1. A `Doctor` class with a dependency on a `Nurse` class.
2. Both classes are managed as Spring beans via Java configuration in a `BeanConfig` class.
3. Dependencies are injected programmatically.

---

### 1. Create the `Doctor` Class
This class has a `qualification` and depends on a `Nurse`.

```java
package demo;

public class Doctor {
    private String qualification;
    private Nurse nurse;

    // Constructor for dependency injection
    public Doctor(String qualification, Nurse nurse) {
        this.qualification = qualification;
        this.nurse = nurse;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void assist() {
        System.out.println("Doctor with qualification " + qualification + " is assisting.");
        nurse.assist();
    }
}
```

---

### 2. Create the `Nurse` Class
This class provides additional functionality.

```java
package demo;

public class Nurse {
    public void assist() {
        System.out.println("Nurse is assisting the doctor.");
    }
}
```

---

### 3. Create the `BeanConfig` Class
This configuration class uses Spring's Java-based configuration annotations.

```java
package demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    // Define the Nurse bean
    @Bean
    public Nurse nurse() {
        return new Nurse(); // Returning a new Nurse instance
    }

    // Define the Doctor bean and inject Nurse into it
    @Bean
    public Doctor doctor() {
        return new Doctor("MBBS", nurse()); // Passing Nurse bean to Doctor
    }
}
```

---

### 4. Create the Main Application Class
Load the Spring context using the `BeanConfig` class, retrieve the beans, and call their methods.

```java
package demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // Load the Spring context from the BeanConfig class
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);

        // Retrieve the Doctor bean
        Doctor doctor = context.getBean(Doctor.class);
        doctor.assist(); // Call the method
    }
}
```

---

### Output
When you run the `App` class, the output will be:

```
Doctor with qualification MBBS is assisting.
Nurse is assisting the doctor.
```

---

### Key Annotations and Concepts

1. **`@Configuration`**
    - Marks the class as a source of Spring beans.
    - Spring treats it like an XML configuration file but in Java.

2. **`@Bean`**
    - Used to define and register a bean in the Spring IoC container.
    - The method name (e.g., `nurse()`) becomes the **bean ID**.

3. **Method Injection**
    - Beans can be injected into others by calling the bean method explicitly (e.g., `nurse()` in `doctor()`).

4. **Context Loading**
    - The context is loaded using `AnnotationConfigApplicationContext`, which takes a configuration class (e.g., `BeanConfig`) as input.

---

### Benefits of Java-Based Configuration

1. **Type Safety:** Method-based configuration allows compile-time type checking, unlike XML configuration.
2. **Refactoring-Friendly:** IDEs easily handle refactoring of class/method names in Java code.
3. **No XML Files:** Developers can work entirely in Java without relying on external configuration files.
4. **Readable and Maintainable Code:** All bean definitions and dependencies are managed in one place, making it easier to understand and maintain.

---

### Notes

1. **Bean Scopes:**
   By default, beans created by `@Bean` are singleton scoped. To configure a different scope (e.g., prototype), use the `@Scope` annotation:

   ```java
   @Bean
   @Scope("prototype")
   public Nurse nurse() {
       return new Nurse();
   }
   ```

2. **Alternative to `@Bean`:**
   Instead of defining beans in `BeanConfig` using `@Bean`, you can annotate your classes with stereotypes like `@Component` and enable component scanning via `@ComponentScan`.

3. **Spring Boot Compatibility:**
   Java-based configuration is standard in Spring Boot applications, where it's commonly used with annotations like `@SpringBootApplication` to include all configuration implicitly.

---

This approach is modern, clean, and aligns with the best practices in Spring Framework development!