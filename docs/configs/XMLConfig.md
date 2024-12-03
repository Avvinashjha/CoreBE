# XML Based Configuration

XML-based configuration is one of the older but still supported methods for configuring a Spring application. Below is an example of an XML-based configuration where we define beans and manage dependency injection through an XML file.

---
### XML Configuration Example: Simple Application
Let’s create a simple example to demonstrate:

#### Steps to Implement
1. A service (`HelloService`) that provides a greeting message.
2. A main application (`App`) that uses the service to display a message.
3. Define the Spring beans and configuration in an XML file.
4. Use the Spring IoC container to manage the beans.

---

### Step 1: Create a Service Class
The service class contains the logic for generating the greeting.

```java
// HelloService.java
public class HelloService {
    private String message;

    // Setter for Dependency Injection
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
```

---

### Step 2: Create the Main Application Class
The main application class pulls the bean from the Spring IoC container and calls the service.

```java
// App.java
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        // Load the Spring XML configuration
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        // Retrieve the HelloService bean
        HelloService helloService = (HelloService) context.getBean("helloService");
        
        // Use the HelloService bean to print the message
        System.out.println(helloService.getMessage());
    }
}
```

---

### Step 3: Create the Spring XML Configuration File
Here is the XML configuration file (`applicationContext.xml`) that defines the `HelloService` bean and its properties.

```xml
<!-- applicationContext.xml -->
<beans xmlns="http://www.springframework.org/schema/beans" 
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
       http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <!-- Define the HelloService bean -->
    <bean id="helloService" class="HelloService">
        <!-- Set the message property -->
        <property name="message" value="Hello, Spring Framework!"/>
    </bean>
</beans>
```

---

### Step 4: Configure Dependencies
Make sure you include the Spring Core library in your project. If you are using Maven, add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.29</version> <!-- Use the latest version -->
</dependency>
```

---

### Step 5: Run the Application
When you run the `main` method in `App.java`, the output will be:

```
Hello, Spring Framework!
```

---

### What Happens Here?

1. **Spring IoC Container:**
    - The `ClassPathXmlApplicationContext` is used to load the `applicationContext.xml` file. The Spring IoC container parses and instantiates the beans defined in the XML.

2. **Bean Definition:**
    - The `<bean>` tag defines `HelloService` as a bean with an id of `helloService`.
    - The `property` tag sets the value of the `message` property in the `HelloService` class.

3. **Dependency Injection:**
    - The Spring IoC container injects the value `"Hello, Spring Framework!"` into the `message` property of `HelloService` using the setter method (`setMessage`).

4. **Bean Retrieval:**
    - The application retrieves the `HelloService` bean using `context.getBean("helloService");` and uses it to access the `getMessage` method.

---

### Notes
- XML-based configuration was widely used before annotation-based and Java-based configurations became popular.
- In modern Spring applications, XML configurations have largely been replaced by **annotations** (e.g., `@Component`, `@Autowired`) and **Java-based configuration** (e.g., `@Configuration`, `@Bean`).

---

If you're working on learning newer approaches, consider exploring **Spring Boot**, which eliminates most of the boilerplate configuration (like this XML file). However, understanding XML-based configuration is helpful for maintaining older projects or foundational learning.