package com.example.TaskManager;

public class Task {
    String name;
    String category;
    String   date;
    String priority;
    String state;

    public Task(String name, String category, String date, String priority, String state){
            this.name = name;
            this.category=category;
            this.date=date;
            this.priority=priority;
            this.state=state;

    }
    public  String getName(){
        return name;
    }
    public  String getCategory(){
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getPriority() {
        return priority;
    }

    public String getState() {
        return state;
    }
}
