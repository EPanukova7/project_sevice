package application.ui.controller;

import application.ui.Validation;
import application.ui.entity.Comment;
import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.entity.User;
import application.ui.service.TaskService;
import application.ui.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class TaskController {
    final TaskService taskService;
    final UserService userService;

    public TaskController(TaskService taskService,
                          UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/projects/{projectId}/tasks/create")
    public ModelAndView createGet(@PathVariable("projectId") Project project,
                                  @ModelAttribute Task task,
                                  @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        if (userId == -1) {
            return new ModelAndView("redirect:/login");
        }
        User user = userService.getById(userId);

        HashMap<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("project", project);
        return new ModelAndView("tasks/create", params);
    }

    @PostMapping("/projects/{projectId}/tasks/create")
    public ModelAndView createPost(@PathVariable("projectId") Project project,
                                   @Valid Task task,
                                   @CookieValue(value = "userId", defaultValue = "-1") int userId,
                                   BindingResult result) {
        if (userId == -1) {
            return new ModelAndView("redirect:/login");
        }
        if (!Validation.isCorrectName((task.getName()))) {
            result.addError(new FieldError("task", "name", "Incorrect task name. Use a-zA-Z0-9_-"));
        }
        if (result.hasErrors()) {
            return new ModelAndView("tasks/create", "formErrors", result.getAllErrors());
        }
//        TODO: check permissions
        User user = userService.getById(userId);
        task = taskService.create(project, task, user);

        HashMap<String, Object> params = new HashMap<>();
        params.put("projectId", project.getId());
        params.put("taskId", task.getId());
        return new ModelAndView("redirect:/projects/{projectId}/tasks/{taskId}", params);
    }

    @GetMapping("/projects/{projectId}/tasks/{taskId}")
    public ModelAndView viewGet(@PathVariable("projectId") Project project,
                                @PathVariable("taskId") Task task,
                                @ModelAttribute Comment comment,
                                @ModelAttribute User taskExecutor,
                                @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        if (userId == -1) {
            return new ModelAndView("redirect:/login");
        }
        User user = userService.getById(userId);

        HashMap<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("project", project);
        params.put("task", task);
        params.put("taskComments", task.getComments());
        params.put("taskStatuses", taskService.getPossibleStatusesByTaskStatus(task.getStatus()));
        params.put("taskExecutor", task.getExecutor());
        return new ModelAndView("tasks/view", params);
    }

    @PostMapping("projects/{projectId}/tasks/{taskId}/comments/create")
    public ModelAndView createComment(@PathVariable("projectId") Project project,
                                      @PathVariable("taskId") Task task,
                                      @Valid Comment comment,
                                      @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        if (userId == -1) {
            return new ModelAndView("redirect:/login");
        }
        User user = userService.getById(userId);
        taskService.createComment(task, comment, user);

        HashMap<String, Object> params = new HashMap<>();
        params.put("projectId", project.getId());
        params.put("taskId", task.getId());
        return new ModelAndView("redirect:/projects/{projectId}/tasks/{taskId}", params);
    }

    @PostMapping("/projects/{projectId}/tasks/{taskId}/executor")
    public ModelAndView changeStatusAndUser(@PathVariable("projectId") Project project,
                                            @PathVariable("taskId") @Valid Task task,
                                            @Valid User executor,
                                            @CookieValue(value = "userId", defaultValue = "-1") int userId){
        taskService.updateExecutor(task, executor);
        return new ModelAndView("redirect:/projects/{projectId}/tasks/{taskId}");
    }

    @PostMapping(value = "projects/{projectId}/tasks/{taskId}/comments/{commentId}")
    public ModelAndView deleteComment(@PathVariable("projectId") Project project,
                                      @PathVariable("taskId") Task task,
                                      @PathVariable("commentId") Comment comment,
                                      @CookieValue(value = "userId", defaultValue = "-1") int userId,
                                      RedirectAttributes redirect) {
        if (userId == -1) {
            return new ModelAndView("redirect:/login");
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("projectId", project.getId());
        params.put("taskId", task.getId());

        if (userId != comment.getOwner().getId()) {
            redirect.addFlashAttribute("errorMessage", "Permission denied");
            return new ModelAndView("redirect:/projects/{projectId}/tasks/{taskId}", params);
        }

        taskService.deleteComment(comment);
        return new ModelAndView("redirect:/projects/{projectId}/tasks/{taskId}", params);
    }

    @GetMapping(value = "/tasks/my_tasks")
    public ModelAndView myTasksGet(@CookieValue(value = "userId", defaultValue = "-1") int userId) {
        if (userId == -1) {
            return new ModelAndView("redirect:/login");
        }
        User user = userService.getById(userId);
        Iterable<Project> projects = user.getProjects();
        HashMap<Project, List<Task>> tasksByProjects = new HashMap<>();
        for (Task task : user.getExecutingTasks()){
            if (tasksByProjects.containsKey(task.getProject())){
                tasksByProjects.get(task.getProject()).add(task);
            } else {
                List<Task> tasks = new ArrayList<>();
                tasks.add(task);
                tasksByProjects.put(task.getProject(), tasks);
            }
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("projects", projects);
        params.put("tasksByProjects", tasksByProjects);
        return new ModelAndView("tasks/my_tasks", params);
    }
}
