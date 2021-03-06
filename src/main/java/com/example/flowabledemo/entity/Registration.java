package com.example.flowabledemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.flowable.task.api.Task;

import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Registration {

    private UUID registrationProcessId;

    private UUID taskID;

    private String author;

    private String title;

    private String articleBody;

    private String status;

    private String email;

    public Registration(Task task){

        Map<String, Object> articleVariables = task.getProcessVariables();
        this.registrationProcessId = UUID.fromString(task.getProcessInstanceId());
        this.taskID = UUID.fromString(task.getId());
        this.author = (String) articleVariables.get("author");
        this.title = (String) articleVariables.get("tittle");
        this.articleBody = (String) articleVariables.get("articleBody");
        this.status = (String) articleVariables.get("status");
        this.email = (String) articleVariables.get("email");
    }
}
