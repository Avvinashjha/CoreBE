package annotationBasedConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DocStudent implements Student{
    @Value("Avinash")
    private String name;
    @Value("123")
    private int id;

    @Override
    public void getAttendance() {
        System.out.println("Doctor student gets attendance");
    }

    @Override
    public void getGrade() {
        System.out.println("Doctor student gets grade");
    }

    @Override
    public void study() {
        System.out.println("Doctor student studies");
    }

    public DocStudent() {
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
        return "DocStudent{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
