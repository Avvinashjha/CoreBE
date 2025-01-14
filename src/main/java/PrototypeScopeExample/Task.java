package PrototypeScopeExample;

public class Task {
    private String name;
    private String description;

    public Task(){
        System.out.println("Creating a new Task instance: "+ this.hashCode());
    }

    // getter and setter methods

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // display task information
    public void displayTaskInfo(){
        System.out.println("Task Info: " + this.hashCode() + " | Name: " + name + " | Description: " + description);
    }
}
