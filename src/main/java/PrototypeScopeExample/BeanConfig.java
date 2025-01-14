package PrototypeScopeExample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "PrototypeScopeExample")
public class BeanConfig {

    @Bean(name="taskPrototype")
    @Scope("prototype")
    public Task task(){
        return new Task();
    }

}
