### What is **Bean Scope** in Spring?

In Spring, **bean scope** defines the lifecycle and visibility of a bean in the Spring IoC (Inversion of Control) container. It determines:
- **How many instances** of a bean are created.
- **How beans are shared** across different parts of an application.

By default, Spring beans are **singleton** scoped, meaning only one instance of the bean exists per Spring container.

---

### Types of Bean Scopes in Spring

There are **6 types** of bean scopes in Spring:

1. **Singleton** (Default)
2. **Prototype**
3. **Request** (Web Applications only)
4. **Session** (Web Applications only)
5. **Application** (Web Applications only)
6. **WebSocket** (WebSocket Applications only)

Let's discuss each in detail:

---

### 1. **Singleton** (Default Scope)
- **Scope ID:** `singleton`
- **Definition:** The Spring container creates only **one instance** of the bean for the entire Spring container, and this single instance is shared across the application.
- **Use case:**
    - Useful for stateless beans, such as **service classes, DAOs, and utility classes**.
    - Preferred when bean functionality doesn’t change state over time.

- **Example:**
```java
@Configuration
public class BeanConfig {
    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```

- Even if `myService()` is accessed multiple times in different parts of the application, only **one instance** will exist in the container.

---

### 2. **Prototype**
- **Scope ID:** `prototype`
- **Definition:** A **new instance** of the bean is created every time it is requested from the container (or injected into another bean).
- **Use case:**
    - Used for stateful beans where each invocation requires a **separate instance**.
    - Example: When you need unique, non-shared objects, such as forms for **user inputs in desktop applications** or separate instances for **complex workflows**.

- **Example:**
```java
@Bean
@Scope("prototype")
public MyForm myForm() {
    return new MyForm();
}
```

- Every time the `myForm` bean is requested, **a new instance** will be created.

- **Note:** Prototype beans are not managed by the container once injected. It doesn’t manage the complete lifecycle (e.g., destruction).

---

### 3. **Request** (Web Application only)
- **Scope ID:** `request`
- **Definition:** A **single instance** of the bean is created per **HTTP request**.
- **Use case:**
    - Suitable for web applications where each user request needs its own independent state, such as beans storing **request-specific data**.
    - Commonly used in **Spring MVC controllers** or REST services to process HTTP requests.

- **Example:**
```java
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestScopedBean {
    public void processRequest() {
        System.out.println("Request ID: " + this.hashCode());
    }
}
```

- A new instance will be created for each HTTP request, ensuring data isolation between requests.

---

### 4. **Session** (Web Application only)
- **Scope ID:** `session`
- **Definition:** A **single instance** of the bean is created and shared within the **entire HTTP session**. A new instance is created when a **new session** starts.
- **Use case:**
    - Suitable for storing **session-specific data**, like user profiles or shopping carts in web applications.

- **Example:**
```java
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionScopedBean {
    private String username;

    // Getter and Setter for User Session Data
}
```

- A new instance of `SessionScopedBean` is created for each **new HTTP session** and shared across all requests for that session.

---

### 5. **Application** (Web Application only)
- **Scope ID:** `application`
- **Definition:** A **single instance** of the bean is created for the **entire lifecycle of the ServletContext** in a web application. It is essentially a "shared singleton scoped to the application."
- **Use case:**
    - Use for beans that are shared across the entire web application and accessed by all sessions and requests, such as global configurations or application statistics.

- **Example:**
```java
@Component
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ApplicationScopedBean {
    public void trackApplicationStats() {
        System.out.println("Application-specific tasks: " + this.hashCode());
    }
}
```

- The bean exists for the **entire lifecycle** of the application.

---

### 6. **WebSocket** (WebSocket Applications only)
- **Scope ID:** `websocket`
- **Definition:** A **single instance** of the bean is created and shared for each **WebSocket session**.
- **Use case:**
    - Suitable for **WebSocket-based applications** to maintain user session states during a WebSocket communication process.

- **Example:**
```java
@Component
@Scope(value = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WebSocketScopedBean {
    private String sessionData;

    // Logic to manage WebSocket session data
}
```

---

### Summary of Bean Scopes

| Scope       | ID         | Description                                                                                   | Use Cases                                 |
|-------------|------------|-----------------------------------------------------------------------------------------------|------------------------------------------|
| **Singleton** | `singleton` | Single instance per Spring container (default).                                              | Stateless beans (e.g. services, DAOs).  |
| **Prototype** | `prototype` | New instance of bean every time it is requested.                                             | Stateful beans (e.g. individual forms). |
| **Request**   | `request`   | Single instance per HTTP request (web-only).                                                 | Request-specific data in web apps.      |
| **Session**   | `session`   | Single instance per HTTP session (web-only).                                                 | User session data (e.g. shopping cart). |
| **Application** | `application` | Single instance per application context (web-only).                                         | Application-wide shared data.           |
| **WebSocket**  | `websocket`  | Single instance per WebSocket session (web-only).                                           | WebSocket communication data.           |

---

### Use Cases for Different Scopes

#### 1. Singleton:
- **Service classes, DAO objects, and utility classes** that do not maintain any internal state.

#### 2. Prototype:
- Useful for situations where each user or client needs a **unique bean instance**, such as **custom workflows, random values, or unique states**.

#### 3. Request:
- Good for **request-sensitive data** such as logging request details or tracking specific client actions throughout a **single HTTP request**.

#### 4. Session:
- Needed for storing **user session-related information**, such as login credentials, cart items, or preferences, in **web-based applications**.

#### 5. Application:
- Use when you need **global application-wide data** that is accessed and shared for the **entire lifecycle of the web application**.

#### 6. WebSocket:
- Used for **real-time communication sessions**, such as maintaining chat messages or session-specific live data.

---

### How to Change Bean Scope?

You can change the scope of a Spring bean using the `@Scope` annotation on top of a `@Component` or `@Bean`. For example:

```java
@Component
@Scope("prototype")
public class MyPrototypeBean {
    // Prototype scoped bean
}

// Or for Java-based configuration
@Bean
@Scope("prototype")
public MyPrototypeBean myBean() {
    return new MyPrototypeBean();
}
```

For **web-specific scopes**, ensure you're using a **web-aware Spring context** (e.g., `WebApplicationContext`) with `@Scope` values like `"request"`, `"session"`, etc.

---

Understanding bean scopes enhances the efficiency of your applications by ensuring proper lifecycle management for each bean based on its responsibilities. Always choose the **right scope** for the specific use case to optimize memory, performance, and maintainability.