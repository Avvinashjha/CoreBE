package SingletonScopeExample;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);

        // retrieve the order service and call the method
        OrderService orderService = context.getBean(OrderService.class);
        orderService.order("Laptop");

        // Retrieve the Notification service and call the method
        NotificationService notificationService = context.getBean(NotificationService.class);
        notificationService.notifyUser("Avinash");

        // Retrieve the Logger Service directly from the spring container
        LoggerService loggerService = context.getBean(LoggerService.class);
        loggerService.log("Application started");
    }
}
