package BeanLifeCycleExample;

import org.springframework.beans.factory.BeanNameAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Test implements BeanNameAware {

    @Override
    public void setBeanName(String name) {
        System.out.println("Set Bean Name method is called"+name);
    }

    public Test() {
        System.out.println("1. Test Bean is being initialized.");
    }
    // Post Construct annotation-based method for initialization@
    @PostConstruct
    public void init() {
        System.out.println("2. Custom initialization logic (@PostConstruct).");
    }

    public void performTask(){
        System.out.println("3. Bean Ready for use.");
    }

    // PreDestroy annotation-based method for cleanup
    @PreDestroy
    public void cleanup(){
        System.out.println("4. Custom Destruction logic (@preDestroy).");
    }


}
