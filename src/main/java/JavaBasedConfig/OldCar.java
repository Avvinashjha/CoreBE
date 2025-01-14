package JavaBasedConfig;

import org.springframework.stereotype.Component;

public class OldCar implements Car{
    private String name;
    @Override
    public void drive(String name) {
        System.out.println(name + " drives a old car");
    }

    public OldCar(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
