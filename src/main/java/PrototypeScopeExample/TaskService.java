package PrototypeScopeExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TaskService {
    @Autowired
    private ApplicationContext context;

    public void createTask(String name, String description){
        Task task = context.getBean("taskPrototype", Task.class);
        task.setName(name);
        task.setDescription(description);
        task.displayTaskInfo();
    }
}
