package SingletonScopeExample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "SingletonScopeExample")
public class BeanConfig {

    @Bean
    public LoggerService loggerService(){
        return new LoggerService();
    }
}
