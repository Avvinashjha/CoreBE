## Singleton Scope

Below is an example to demonstrate the **singleton scope** in Spring, which is the **default bean scope**. We'll create a simple example involving a `LoggerService`, which logs messages. Regardless of how many times the bean is accessed, **only a single instance of the bean will be created** and shared across the application.

---

### Use Case: Logger Service in Singleton Scope
We want a `LoggerService` that handles logging in a system. Since logging is a **stateless** operation, we will use the **singleton scope** to ensure there is only one instance of the `LoggerService` shared across the application.

---

### Steps

#### 1. Create the Logger Service (Singleton Bean)
This `LoggerService` class will contain one `log()` method, which prints a message and the hash code of the current instance to demonstrate its singleton behavior.

```java
package demo;

public class LoggerService {
    public void log(String message) {
        System.out.println("LoggerService instance: " + this.hashCode() + " - Logging message: " + message);
    }
}
```

---

#### 2. Define the Configuration Class
We explicitly define the `LoggerService` bean in the configuration class using `@Bean`. Since the default scope is **singleton**, the same instance will be reused every time we request the bean.

```java
package demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    // Singleton scoped bean: Default scope is singleton
    @Bean
    public LoggerService loggerService() {
        return new LoggerService();
    }
}
```

---

#### 3. Create a Component that Uses the Logger Service
Here, `OrderService` and `NotificationService` classes will both use the `LoggerService`. Since `LoggerService` is **singleton-scoped**, the **same instance** will be shared between `OrderService` and `NotificationService`.

##### OrderService.java
```java
package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {
    @Autowired
    private LoggerService loggerService; // Dependency

    public void order(String item) {
        loggerService.log("Order placed for: " + item);
    }
}
```

##### NotificationService.java
```java
package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {
    @Autowired
    private LoggerService loggerService; // Dependency

    public void notifyUser(String user) {
        loggerService.log("Notification sent to: " + user);
    }
}
```

---

#### 4. Add Component Scanning
To let Spring automatically detect and register the `OrderService` and `NotificationService` classes, enable **component scanning**:

```java
package demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "demo") // Scan the 'demo' package for components
public class AppConfig {
}
```

---

#### 5. Main Application
Load the Spring container using the configuration and retrieve the beans to test their behavior.

```java
package demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // Load the Spring context
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Retrieve the OrderService and call its method
        OrderService orderService = context.getBean(OrderService.class);
        orderService.order("Laptop");

        // Retrieve the NotificationService and call its method
        NotificationService notificationService = context.getBean(NotificationService.class);
        notificationService.notifyUser("Alice");

        // Retrieve the LoggerService directly from the Spring container
        LoggerService loggerService = context.getBean(LoggerService.class);
        loggerService.log("This is a direct log call.");
    }
}
```

---

### Expected Output
When the `main()` method is executed, the output will demonstrate that the `LoggerService` retains the same instance (indicated by the hash code) across all usages.

```
LoggerService instance: 12345678 - Logging message: Order placed for: Laptop
LoggerService instance: 12345678 - Logging message: Notification sent to: Alice
LoggerService instance: 12345678 - Logging message: This is a direct log call.
```

Here, the hash code (`12345678`) is the same across all calls to the `LoggerService` bean, showing that all services (`OrderService`, `NotificationService`) share the **same instance of the LoggerService**.

---

### Explanation of Singleton Behavior
1. **Single Instance:**
   The `LoggerService` bean is created **only once** when the Spring IoC container is initialized. This single instance is shared across multiple services (shared dependency).

2. **Lazy Initialization:**
   By default, the singleton bean is created when the Spring container is initialized (eager initialization). You can configure **lazy initialization** by adding `@Lazy`.

3. **Default Behavior:**
   The singleton scope is the default scope in Spring, so you don't need to explicitly declare the scope as `@Scope("singleton")`.

---

### When to Use Singleton Scope?
- **Stateless Beans:**
    - Service classes: Handle business logic and process requests without storing any state.
    - DAO classes: Represents data access components.
- **Utility Classes:**
    - Logging, configurations, or a class performing computations.

Singleton is the most commonly used scope for beans in Spring applications, as it minimizes memory consumption and ensures thread safety by sharing the same instance.

--- 

**Singleton Scope Summary:**
- **Default bean scope** in Spring.
- One shared instance per Spring IoC container.
- Reduces memory footprint for stateless and reusable beans.
- Not suitable for stateful components (use `prototype` for that).