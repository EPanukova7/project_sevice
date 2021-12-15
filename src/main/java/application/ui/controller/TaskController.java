package application.ui.controller;

import application.ui.Validation;
import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.entity.User;
import application.ui.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
public class TaskController {

    @GetMapping(value = "/projects/{projectId}/tasks/create")
    public ModelAndView create_get(@PathVariable("projectId") Project project, @ModelAttribute Task task) {
        return new ModelAndView("tasks/create", "project", project);
    }

    @PostMapping(value = "/projects/{projectId}/tasks/create")
    public ModelAndView create_post(@PathVariable("projectId") Project project,
                                    @Valid Task task,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("tasks/create", "formErrors", result.getAllErrors());
        }
        if (!Validation.isCorrectName(task.getName())){
            return new ModelAndView("projects/list", "error", "Incorrect task name. Use a-zA-Z0-9_-");
        }
        task = TaskService.create(project, task);
        HashMap<String, Object> params = new HashMap<>();
        params.put("projectId", project.getId());
        params.put("taskId", task.getId());
        return new ModelAndView("redirect:/projects/{projectId}/tasks/{taskId}", params);
    }

    @GetMapping(value = "/projects/{projectId}/tasks/{taskId}")
    public ModelAndView view_get(@PathVariable("projectId") Project project,
                                 @PathVariable("taskId") Task task) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("project", project);
        params.put("task", task);
        return new ModelAndView("tasks/view", params);
    }
}
