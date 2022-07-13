package service;

import entity.Registration;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    public Registration startProcess(Registration registration) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("author", registration.getAuthor());
        variables.put("tittle", registration.getTitle());
        variables.put("articleBody", registration.getArticleBody());
        variables.put("status", "new");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("articleReview", variables);
        registration.setArticleProcessID(UUID.fromString(processInstance.getId()));

        return registration;
    }

    public List<Registration> fetchTasksForAsigne(String asigne) {
        List<Task> tasks = taskService.createTaskQuery()
                .taskCandidateGroup(asigne)
                .list();

        return tasks.stream().map(task -> {Map<String, Object> variables = taskService.getVariables(task.getId());

        return new Registration((UUID.fromString(
                task.getProcessInstanceId())),
                UUID.fromString(task.getId()),
                (String) variables.get("author"), (String) variables.get("title"),
                (String) variables.get("articleBody"), (String) variables.get("status"));
        })
                .collect(Collectors.toList());
    }

    public void decisionMaking(String articleProcessId, String decision) {
        Task task = taskService.createTaskQuery()
                .processInstanceId(articleProcessId)
                .includeProcessVariables()
                .singleResult();
        String taskId = task.getId();

        Map<String, Object> variables = new HashMap<>();
        variables.put("status", decision);
        taskService.complete(task.getId(), variables);

    }

    public List<Registration> listOfNewRegistrations() {
        List<Task> tasks = taskService.createTaskQuery()
                .includeProcessVariables()
                .processVariableValueEquals("status" , "new")
                .list();
        return tasks.stream().map(Registration::new).collect(Collectors.toList());
    }

    public List<Registration> listOfRegistrationsByAuthor(String authorName) {
        List<Task> tasks = taskService.createTaskQuery()
                .includeProcessVariables()
                .processVariableValueEquals("author", authorName)
                .list();
        return tasks.stream().map(Registration::new).collect(Collectors.toList());
    }
}
