package JavaBasedConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "JavaBasedConfig")
public class BeanConfig {
        @Bean
        public NewCar newCar(){
            NewCar car = new NewCar();
            car.setName("sedan");
            return car;
        }
        @Bean
        public OldCar oldCar(){
            return new OldCar("SUV");
        }
}
