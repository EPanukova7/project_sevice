package application.ui.controller;

import application.ui.entity.Comment;
import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.entity.User;
import application.ui.service.CommentService;
import application.ui.service.ProjectService;
import application.ui.service.TaskService;
import application.ui.service.TaskStatusService;
import application.ui.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class TaskController {

    @GetMapping(value = "/projects/{projectId}/tasks/create")
    public ModelAndView createGet(@PathVariable("projectId") Project project,
                                   @ModelAttribute Task task,
                                   @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        if (userId == -1) {
            return new ModelAndView("redirect:login");
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("project", project);
        return new ModelAndView("tasks/create", params);
    }

    @PostMapping(value = "/projects/{projectId}/tasks/create")
    public ModelAndView createPost(@PathVariable("projectId") Project project,
                                    @Valid Task task,
                                    @CookieValue(value = "userId", defaultValue = "-1") int userId,
                                    BindingResult result) {
        if (userId == -1) {
            return new ModelAndView("redirect:login");
        }
        if (result.hasErrors()) {
            return new ModelAndView("tasks/create", "formErrors", result.getAllErrors());
        }
        task = TaskService.create(project, UserService.getById(userId), TaskStatusService.getById(1), task);
        HashMap<String, Object> params = new HashMap<>();
        params.put("projectId", project.getId());
        params.put("taskId", task.getId());
        return new ModelAndView("redirect:/projects/{projectId}/tasks/{taskId}", params);
    }

    @GetMapping(value = "/projects/{projectId}/tasks/{taskId}")  // Вроде решили на эту страницу передавать комменты
    public ModelAndView viewGet(@PathVariable("projectId") Project project,
                                 @PathVariable("taskId") Task task,
                                 @ModelAttribute Comment comment,
                                 @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        if (userId == -1) {
            return new ModelAndView("redirect:login");
        }
        HashMap<String, Object> params = new HashMap<>();
        User user = UserService.getById(userId);
        params.put("user", user);
        params.put("project", project);
        params.put("task", task);
        params.put("comments", CommentService.getAllByTaskId(task.getId()));
        params.put("projectUsers", project.getUsers());
        params.put("taskStatuses", TaskStatusService.getAllById(task.getStatus().getId()));
        params.put("taskOwner", task.getOwner());
        return new ModelAndView("tasks/view", params);
    }

    @PostMapping(value = "projects/{projectId}/tasks/{taskId}")
    public ModelAndView createComment(@PathVariable("projectId") Project project,
                                      @PathVariable("taskId") Task task,
                                      @Valid Comment comment,
                                      @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        if (userId == -1) {
            return new ModelAndView("redirect:login");
        }
        HashMap<String, Object> params = new HashMap<>();
        comment = CommentService.create(task, UserService.getById(userId), comment);
        User user1 = UserService.getById(userId);
        params.put("user", user1);
        params.put("project", project);
        params.put("task", task);
        params.put("taskStatuses", TaskStatusService.getAllById(task.getStatus().getId()));
        params.put("comments", CommentService.getAllByTaskId(task.getId()));
        return new ModelAndView("redirect:/projects/{projectId}/tasks/{taskId}", params);
    }

    @PatchMapping("projects/{projectId}/tasks/{taskId}")
    public ModelAndView changeStatusAndUser(@PathVariable("projectId") Project project,
                                            @PathVariable("taskId") @Valid Task task,
                                            @CookieValue(value = "userId", defaultValue = "-1") int userId){
        return new ModelAndView("redirect:/projects/{projectId}/tasks/{taskId}");
    }

    @PostMapping(value = "projects/{projectId}/tasks/{taskId}/comments/{commentId}")
    public ModelAndView deleteComment(@PathVariable("projectId") Project project,
                                      @PathVariable("taskId") Task task,
                                      @PathVariable("commentId") Comment comment,
                                      @CookieValue(value = "userId", defaultValue = "-1") int userId,
                                      RedirectAttributes redirect) {
        if (userId == -1) {
            return new ModelAndView("redirect:login");
        }
        if (userId != comment.getOwner().getId()) {
            redirect.addFlashAttribute("error", "Permission denied");
            return new ModelAndView("redirect:/projects/{projectId}/tasks/{taskId}");
        }

        CommentService.deleteComment(comment);
        return new ModelAndView("redirect:/projects/{projectId}/tasks/{taskId}");
    }

//    @GetMapping(value = "/tasks/my_tasks")
//    public ModelAndView myTasksGet(@CookieValue(value = "userId", defaultValue = "-1") int userId) {
//        if (userId == -1) {
//            return new ModelAndView("redirect:login");
//        }
//        Iterable<Project> projects = ProjectService.getAllByUserId(userId);
//        HashMap<Integer, ArrayList<Task>> projectsTasksMap = new HashMap<>();
//        for (Project project : projects){
//            projectsTasksMap.put(project.getId(), TaskService.getAllByExecutorIdAAndProjectId(userId, project.getId()));
//        }
//        HashMap<String, Object> params = new HashMap<>();
//        params.put("projects", projects);
//        params.put("projectsTasksMap", projectsTasksMap);
//        return new ModelAndView("tasks/my_tasks", params);
//    }
}
