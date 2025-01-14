## What is Bean Lifecycle in Spring?

The **bean lifecycle** in Spring refers to the **stages a bean goes through** from its creation to its destruction, as managed by the Spring IoC (Inversion of Control) container. The lifecycle includes initialization, dependency injection, and final destruction.

Spring provides hooks to customize and control the lifecycle events of a bean, including initialization logic (e.g., after the bean is fully ready) and cleanup logic (e.g., before the bean is destroyed).

---

### Key Stages of the Bean Lifecycle

1. **Instantiation**:
    - The Spring IoC container creates an instance of the bean.
    - This is achieved based on the configuration (e.g., defined in XML, Java class, or annotations).

2. **Dependency Injection (DI)**:
    - Once the bean is instantiated, Spring resolves and injects dependencies as defined in the configuration.

3. **Bean Name Aware** (*Optional*):
    - If the bean implements the `BeanNameAware` interface, Spring provides the name of the bean during this stage.

4. **Bean Factory Aware** (*Optional*):
    - If the bean implements the `BeanFactoryAware` interface, Spring gives access to the `BeanFactory`.

5. **Application Context Aware** (*Optional*):
    - If the bean implements the `ApplicationContextAware` interface, Spring provides access to the `ApplicationContext`.

6. **Custom Initialization (Post-Initialization)**:
    - Immediately after dependency injection, Spring calls the **custom initialization callback** methods such as:
        - `@PostConstruct` (Java’s `javax.annotation` standard annotation)
        - `InitializingBean.afterPropertiesSet()` (Spring interface)
        - Custom `init-method` declared in XML or Java config.
    - This step allows developers to perform initialization tasks after the bean is fully initialized.

7. **Ready-to-Use**:
    - The bean is now fully initialized and ready to be used by the application.

8. **Custom Destruction (Pre-Destroy)**:
    - When the application or context is shutting down (or a scoped bean is destroyed), Spring invokes callbacks for bean destruction, such as:
        - `@PreDestroy` (Java’s `javax.annotation` standard annotation)
        - `DisposableBean.destroy()` (Spring interface)
        - Custom `destroy-method` declared in XML or Java config.

9. **Destruction (End of Lifecycle)**:
    - The bean is destroyed, and all associated resources are cleaned up (e.g., closing connections).

---

### Lifecycle Callback Mechanisms

Spring provides several approaches to implement lifecycle callbacks for initialization or destruction:

1. **Using Annotations**:
    - `@PostConstruct`: Marks a method that should be executed after the bean is fully initialized.
    - `@PreDestroy`: Marks a method that should be executed just before the bean is destroyed.

2. **Using Interfaces**:
    - `InitializingBean` (for initialization): Implement the `afterPropertiesSet()` method.
    - `DisposableBean` (for destruction): Implement the `destroy()` method.

3. **Using XML or Java Config**:
    - Use `init-method` and `destroy-method` attributes in XML or `@Bean(initMethod, destroyMethod)` in Java-based configuration.

---

### Demonstration of Bean Lifecycle

Here’s an example that demonstrates the **bean lifecycle stages**:

#### Step 1: Create a `MyBean` Class
This bean will include custom initialization and destruction logic.

```java
package demo;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class MyBean {

    public MyBean() {
        System.out.println("1. Bean is being instantiated.");
    }

    // PostConstruct annotation-based method for initialization
    @PostConstruct
    public void init() {
        System.out.println("2. Custom initialization logic (@PostConstruct).");
    }

    public void performTask() {
        System.out.println("3. Bean is ready for use.");
    }

    // PreDestroy annotation-based method for cleanup
    @PreDestroy
    public void cleanup() {
        System.out.println("4. Custom destruction logic (@PreDestroy).");
    }
}
```

---

#### Step 2: Define a Configuration Class
Use Java-based configuration to define a bean:

```java
package demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    // Define a MyBean bean
    @Bean
    public MyBean myBean() {
        return new MyBean();
    }
}
```

---

#### Step 3: Main Application
Context initialization and destruction will trigger corresponding lifecycle methods.

```java
package demo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // Initialize the Spring context
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        
        // Retrieve and use the bean
        MyBean myBean = context.getBean(MyBean.class);
        myBean.performTask();

        // Close the Spring context to trigger destruction logic
        context.close();
    }
}
```

---

#### Output
Here’s what happens when you run the program and close the context:

```
1. Bean is being instantiated.
2. Custom initialization logic (@PostConstruct).
3. Bean is ready for use.
4. Custom destruction logic (@PreDestroy).
```

---

### Other Lifecycle Methods

1. **Using `@Bean` with `initMethod` and `destroyMethod`**:
   If you prefer not to use annotations, you can define the lifecycle methods in configuration.

   ```java
   @Bean(initMethod = "initMethod", destroyMethod = "cleanupMethod")
   public MyBean myBean() {
       return new MyBean();
   }
   ```

   And in `MyBean` class:

   ```java
   public void initMethod() {
       System.out.println("Custom initMethod");
   }

   public void cleanupMethod() {
       System.out.println("Custom cleanupMethod");
   }
   ```

2. **Using Spring Interfaces**:
   ```java
   public class MyBean implements InitializingBean, DisposableBean {
       @Override
       public void afterPropertiesSet() {
           System.out.println("InitializingBean: afterPropertiesSet() called");
       }

       @Override
       public void destroy() {
           System.out.println("DisposableBean: destroy() called");
       }
   }
   ```

3. **Using XML Configuration**:
   Add `init-method` and `destroy-method` attributes in the XML file:
   ```xml
   <bean id="myBean" class="demo.MyBean" init-method="initMethod" destroy-method="cleanupMethod" />
   ```

---

### Summary of Bean Lifecycle Events

| Event                       | Triggered When?                                       | Implementation Mechanism         |
|-----------------------------|------------------------------------------------------|----------------------------------|
| **Instantiation**           | When the Spring IoC container creates the bean.      | Constructor                      |
| **Dependency Injection**    | After the bean is created.                           | Setter/Field Injection           |
| **Post Initialization**     | After dependencies are injected.                    | `@PostConstruct`, `initialize()` |
| **Ready-to-Use**            | After initialization and as long as the application is active. | N/A                              |
| **Pre-Destroy**             | Before the container destroys the bean.             | `@PreDestroy`, `DisposableBean`  |

---

### Practical Uses of Bean Lifecycle:
1. **Initialization Tasks**:
    - Setting up resources (e.g., database connections, loading configurations, initializing caches).

2. **Cleanup Tasks**:
    - Releasing resources (e.g., close connections, flush logs, deleting temporary files).

3. **Custom Bean Lifecycle Scenarios**:
    - Configuration of additional properties after dependency injection but before the bean is ready for use.

By understanding the **bean lifecycle**, you can hook into these events and efficiently manage your Spring beans, improving control over resources and application behavior.