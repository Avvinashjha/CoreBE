package JavaBasedConfig;

import org.springframework.stereotype.Component;

public class NewCar implements Car{
    private String name;
    @Override
    public void drive(String name) {
        System.out.println(name + " drives a new car");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
