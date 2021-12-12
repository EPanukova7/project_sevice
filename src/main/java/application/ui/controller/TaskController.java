package application.ui.controller;

import application.ui.entity.Comment;
import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.entity.User;
import application.ui.service.CommentService;
import application.ui.service.TaskService;
import application.ui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TaskController {

    @GetMapping(value = "/projects/{projectId}/tasks/create")
    public ModelAndView create_get(@PathVariable("projectId") Project project, @ModelAttribute Task task) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("project", project);
        return new ModelAndView("tasks/create", params);
    }

    @PostMapping(value = "/projects/{projectId}/tasks/create")
    public ModelAndView create_post(@PathVariable("projectId") Project project,
                                    @Valid Task task,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("tasks/create", "formErrors", result.getAllErrors());
        }
        task = TaskService.create(project, task);
        HashMap<String, Object> params = new HashMap<>();
        params.put("projectId", project.getId());
        params.put("taskId", task.getId());
        return new ModelAndView("redirect:/projects/{projectId}/tasks/{taskId}", params);
    }

    @GetMapping(value = "/projects/{projectId}/tasks/{taskId}")  // Вроде решили на эту страницу передавать комменты
    public ModelAndView view_get(@PathVariable("projectId") Project project,
                                 @PathVariable("taskId") Task task, @Valid Comment comment, @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        HashMap<String, Object> params = new HashMap<>();
        User user = UserService.getById(userId);
        comment = CommentService.create(project, task, user, comment);
        params.put("project", project);
        params.put("task", task);
        params.put("comment", comment);
        return new ModelAndView("tasks/view", params);
    }
}
