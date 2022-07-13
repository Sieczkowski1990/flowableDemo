package controller;

import entity.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.RegistrationService;

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
    public List<Registration> fetchTasks(@RequestBody String asigne) {
        return registrationService.fetchTasksForAsigne(asigne);
    }

    @PostMapping("/decision")
    public void decide (@RequestParam String articleProcessId, String decision) {
        registrationService.decisionMaking(articleProcessId, decision);
    }

    @GetMapping("/newtasks")
    public List<Registration> fetchNewTasks () {return registrationService.listOfNewRegistrations();}

    @GetMapping("/author")
    public List<Registration> fetchRegistrationsByAuthor (@RequestParam String authorName){
        return registrationService.listOfRegistrationsByAuthor(authorName);
    }
}
