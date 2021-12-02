package application.ui.controller;

import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.repository.TaskRepository;
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
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping(value = "/projects/{project_id}/tasks/create")
    public ModelAndView createForm(@PathVariable("project_id") Project project, @ModelAttribute Task task) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("project", project);
        return new ModelAndView("tasks/create", params);
    }

    @PostMapping(value = "/projects/{project_id}/tasks/create")
    public ModelAndView create(@PathVariable("project_id") Project project,
                               @Valid Task task, BindingResult result,
                               RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("tasks/create", "formErrors", result.getAllErrors());
        }
        task = this.taskRepository.save(task);
        HashMap<String, Object> params = new HashMap<>();
        params.put("project_id", project.getId());
        params.put("task_id", task.getId());
        redirect.addFlashAttribute("globalTask", "Successfully created a new task");
        return new ModelAndView("redirect:/projects/{project_id}/tasks/{task_id}", params);
    }

    @GetMapping(value = "/projects/{project_id}/tasks/{task_id}")
    public ModelAndView view(@PathVariable("project_id") Project project,
                             @PathVariable("task_id") Task task) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("project", project);
        params.put("task", task);
        return new ModelAndView("tasks/view", params);
    }
}
