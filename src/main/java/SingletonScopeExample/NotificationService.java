package SingletonScopeExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {
    @Autowired
    private LoggerService loggerService;

    public  void notifyUser(String user){
        loggerService.log("User Notification: " + user);
    }
}
