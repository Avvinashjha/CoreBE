To use **annotation-based configuration** in Spring, we simplify the setup process by eliminating the need for explicit XML-based bean definitions. Instead, we configure the application by using annotations in the Java classes themselves. Below, I'll walk you through how to replace the commented XML configuration in your snippet and implement annotation-based configuration using annotations like `@Component`, `@Autowired`, `@Configuration`, and `@ComponentScan`.

---

### Steps for Annotation-Based Configuration

1. Annotate your classes with **stereotype annotations** like `@Component`.
2. Use dependency injection via **`@Autowired`** for properties or constructors.
3. Replace XML bean definitions with `@ComponentScan` to let Spring automatically scan the package and register your annotated classes as Spring beans.

---

### Revised Approach Based on Your Example (`Doctor`, `Nurse` Classes)

#### Step 1: Define the Doctor and Nurse Classes

##### Doctor.java
You annotate the `Doctor` class with `@Component` so that Spring treats it as a Spring-managed bean. Use constructor injection with `@Autowired`.

```java
package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Doctor {
    private String qualification;
    private Nurse nurse; // Dependency

    // Constructor for injecting the qualification
    @Autowired
    public Doctor(Nurse nurse) {
        this.qualification = "MBBS";
        this.nurse = nurse; // Dependency injection via constructor
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

##### Nurse.java
The `Nurse` class is also annotated with `@Component` so it will be scanned and registered as a Spring bean.

```java
package demo;

import org.springframework.stereotype.Component;

@Component
public class Nurse {

    public void assist() {
        System.out.println("Nurse is assisting the doctor.");
    }
}
```

---

#### Step 2: Configure Annotation-Based Scanning

In your **XML configuration**, you need to **enable component scanning** so that Spring can discover the `@Component`-annotated classes in the `demo` package.

Here is your updated `applicationContext.xml`:

```xml
<?xml version="1.0" encoding="utf-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    
    <!-- Enable component scanning for the "demo" package -->
    <context:component-scan base-package="demo" />
</beans>
```

- The `<context:component-scan>` tag tells Spring to **scan the specified package** (`demo`) for classes annotated with stereotype annotations like `@Component`, `@Controller`, `@Service`, `@Repository`, etc.

---

#### Step 3: Create Main Application Class

Now, you can fetch the `Doctor` bean from the Spring IoC container and access the functionality.

```java
package demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        // Load the Spring context from the XML configuration
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        // Get the Doctor bean
        Doctor doctor = context.getBean(Doctor.class);
        doctor.assist(); // Call the method
    }
}
```

---

### Output

When you run the `App` class, the output will demonstrate how the components work together:

```
Doctor with qualification MBBS is assisting.
Nurse is assisting the doctor.
```

---

### Key Annotations and Concepts Used

1. **`@Component`**:
    - Marks a class as a Spring-managed bean.
    - Equivalent to defining a `<bean>` in an XML configuration.
    - The Spring IoC container will automatically register these beans.

2. **`@Autowired`**:
    - Injects dependencies automatically, either through the **constructor**, **setter**, or **fields**.
    - Spring resolves and provides the required bean from the IoC container.

3. **`@ComponentScan`** (in XML):
    - Scans the specified package(s) for classes annotated with stereotypical annotations like `@Component`.

4. **Dependency Injection via Constructor vs Field**:
    - In the `Doctor` class, we demonstrated **constructor-based injection** using `@Autowired`.
    - This is preferred because it ensures immutability and that the object is always in a valid state.

---

### Moving Away from XML: Use Java-Based Configuration (Optional)

If you want to avoid XML altogether, you can use **Java-based configuration** like this:

#### Configuration Class (`AppConfig`)
```java
package demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "demo") // Replace with your package
public class AppConfig {
}
```

#### Main Application Class
```java
package demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // Load the Spring context using Java-based configuration
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Get the Doctor bean
        Doctor doctor = context.getBean(Doctor.class);
        doctor.assist();
    }
}
```

Now there's no need for `applicationContext.xml`! The annotation-based configuration is now managed via Java.

---

### Benefits of Annotation-Based Configuration

- No need for verbose XML files to define beans.
- Reduced boilerplate code – Spring scans for beans automatically.
- Easier to read and maintain as the configurations are colocated with the actual class definitions.

Annotation-based and Java-based configurations are the modern, preferred approaches for Spring applications. However, XML-based configuration is still supported for legacy projects.