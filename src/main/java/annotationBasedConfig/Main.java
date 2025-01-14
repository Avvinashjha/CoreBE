package annotationBasedConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        EngStudent student1 = context.getBean(EngStudent.class);
        DocStudent student2 = context.getBean(DocStudent.class);
        System.out.println(student1.toString());
        System.out.println(student2.toString());

    }
}
