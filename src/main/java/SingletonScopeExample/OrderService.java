package SingletonScopeExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {
    @Autowired
    private LoggerService loggerService;

    public void order(String item){
        loggerService.log("Order place for: "+ item);
    }
}
