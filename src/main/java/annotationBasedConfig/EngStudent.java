package annotationBasedConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EngStudent implements Student{
    @Value("Lisa")
    private String name;
    @Value("124")
    private int id;

    @Override
    public void getAttendance() {
        System.out.println("EngStudent attends");
    }

    @Override
    public void getGrade() {
        System.out.println("EngStudent gets grade");
    }

    @Override
    public void study() {
        System.out.println("EngStudent studies");
    }

    public EngStudent(){}

    public EngStudent(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EngStudent{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
