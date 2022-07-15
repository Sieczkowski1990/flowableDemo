package com.example.flowabledemo.controller;

import com.example.flowabledemo.entity.Registration;
import com.example.flowabledemo.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/registration")
    public Registration createNewArticle (@RequestBody Registration registration) {
        return registrationService.startProcess(registration);
    }

    @GetMapping("/tasks")
    public List<Registration> fetchTasks(@RequestParam String assignee) {
        return registrationService.fetchTasksForAsigne(assignee);
    }

    @PostMapping("/decision")
    public void decide (@RequestParam String registrationProcessId, String decision) {
        registrationService.decisionMaking(registrationProcessId, decision);
    }

    @GetMapping("/newtasks")
    public List<Registration> fetchNewTasks () {return registrationService.listOfNewRegistrations();}

    @GetMapping("/author")
    public List<Registration> fetchRegistrationsByAuthor (@RequestParam String authorName){
        return registrationService.listOfRegistrationsByAuthor(authorName);
    }
}
