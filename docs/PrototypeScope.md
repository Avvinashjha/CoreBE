## Prototype Scope

In Spring, the **prototype scope** creates a **new instance** of a bean **every time the bean is requested** from the application context. Unlike the **singleton scope**, where a single instance is shared across the entire application, the **prototype scope** ensures **each request** for a bean receives a **distinct and new instance**.

- **Scope Identifier:** `"prototype"` (specified with `@Scope("prototype")`)
- **Lifecycle:**
    - Spring **creates** a new instance of the bean every time it is requested from the IoC container.
    - However, Spring **does not manage the full lifecycle** of prototype-scoped beans (e.g., Spring does not handle bean destruction). After creation and injection, the container no longer tracks it.
- **Use Case:** Suitable for **stateful** beans, where each invocation requires a new and independent instance.

---

### Use Case: Generating Unique Objects (e.g., a Task for Workflows)

Let’s say we’re building a **Task Management System**, where each **Task** object needs to have its **own unique state** (e.g., task name, priority, etc.). We will use the **prototype scope** to ensure that every Task object in the system gets its own instance.

---

### Example: Task Management System

#### Scenario:
- **TaskService** class creates a new task.
- Each **Task** bean has a unique and independent state and cannot be shared.
- Beans with prototype scope ensure that each created `Task` object has no conflicts with other tasks.

---

### Steps to Implement Prototype Scope

#### 1. Define the `Task` Class
This class represents a task object with some properties.

```java
package demo;

public class Task {
    private String name;
    private String description;

    public Task() {
        System.out.println("Creating a new Task instance: " + this.hashCode());
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Display Task Info
    public void displayTaskInfo() {
        System.out.println("Task Info: " + this.hashCode() + " | Name: " + name + " | Description: " + description);
    }
}
```

---

#### 2. Define the Configuration Class Using Prototype Scope

Here we define a prototype-scoped bean for `Task`. This ensures a **new instance** is created every time `getBean()` is called.

```java
package demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfig {

    // Define Task bean with Prototype Scope
    @Bean
    @Scope("prototype")
    public Task task() {
        return new Task();
    }
}
```

---

#### 3. Create the `TaskService` Class
This class demonstrates the dependency on the `Task` bean. It will use the Spring IoC container to get **new Task instances** and populate them.

**Note:** Since `task` is prototype-scoped, every call to `context.getBean(Task.class)` will create a **new instance**.

```java
package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TaskService {

    @Autowired
    private ApplicationContext context; // Inject ApplicationContext to fetch prototype beans

    public void createTask(String name, String description) {
        // Get a new instance of Task
        Task task = context.getBean(Task.class);
        task.setName(name);
        task.setDescription(description);
        task.displayTaskInfo();
    }
}
```

---

#### 4. Define Component Scanning
Enable component scanning so that Spring can discover `TaskService`:

```java
package demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "demo")
public class AppConfig {
}
```

---

#### 5. Main Application Class
Retrieve the `TaskService` bean and use it to create tasks.

```java
package demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // Load the Spring context
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Get the TaskService bean
        TaskService taskService = context.getBean(TaskService.class);

        // Create multiple tasks
        taskService.createTask("Complete Report", "Finish the Q3 financial report.");
        taskService.createTask("Fix Bugs", "Resolve issues flagged in the latest build.");
        taskService.createTask("Prepare Presentation", "Prepare slides for the client meeting.");
    }
}
```

---

#### Output

When you run the program, notice that **each task gets a new instance**, as indicated by differing hash codes:

```
Creating a new Task instance: 12345678
Task Info: 12345678 | Name: Complete Report | Description: Finish the Q3 financial report.

Creating a new Task instance: 87654321
Task Info: 87654321 | Name: Fix Bugs | Description: Resolve issues flagged in the latest build.

Creating a new Task instance: 13579246
Task Info: 13579246 | Name: Prepare Presentation | Description: Prepare slides for the client meeting.
```

The logs explicitly show that **each task gets a unique instance**, satisfying the use case for **stateful objects**.

---

### Key Concepts Demonstrated

1. **Prototype Lifecycle:**
    - A new bean instance is created when requested via `context.getBean()`.
    - After creation, Spring no longer manages the lifecycle of the bean (e.g., destruction isn't automatically handled).

2. **Dependency Management with `ApplicationContext`:**
    - In this example, we used the `ApplicationContext` to explicitly fetch the prototype-scoped bean.
    - This is preferred over injecting a bean of prototype scope directly (`field injection`) because injecting a prototype-scoped bean into a singleton-scoped bean would result in only one instance being created.

3. **Stateful Behavior:**
    - Tasks (`Task` beans) are stateful, meaning each task has its **own independent values**, such as `name` and `description`.

---

### Why Not Use Singleton for This Use Case?
Using a **singleton** in this situation would be problematic because:
- All tasks would reference the **same instance**, leading to overwriting of state or collisions.
- Stateful operations require **new and independent instances**, which singletons don't provide.

---

### Use Cases for Prototype Scope

1. **Stateful Beans**:
    - When each instance of a bean must maintain its **own unique state**, e.g., forms, workflows, or jobs.

2. **Object Customization**:
    - When you need to customize the properties of objects dynamically per request, e.g., dynamically-generated emails or notifications.

3. **Transient Objects**:
    - Objects that are required for a **short-lived task** and are not meant to be reused.

---

### Limitations of Prototype Scope
1. **Lifecycle Management:**
    - Spring doesn’t manage the destruction of prototype beans. You must handle cleanup manually if required.

2. **Integration with Singleton Beans:**
    - Injecting a prototype bean into a singleton bean directly is not recommended because the same instance will be used repeatedly.

---

### Summary
The prototype scope ensures **complete separation of state** by creating a **new instance** every time the bean is requested. It is ideal for stateful beans or cases where transient, per-use objects are required. This behavior is critical in situations like **task management, form handling, or workflows** where each instance must be independent and unique.