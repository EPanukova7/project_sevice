package application.ui.controller;

import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.entity.User;
import application.ui.service.ProjectService;
import application.ui.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
public class ProjectController {
    @GetMapping(value = "/")
    public ModelAndView index() {
        return new ModelAndView("redirect:projects");
    }

    @GetMapping(value = "/projects")
    public ModelAndView list_get(@ModelAttribute Project project, @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        if (userId == -1) {
            return new ModelAndView("redirect:login");
        }
        Iterable<Project> projects = ProjectService.getAllByUserId(userId);
        String userEmail = UserService.getById(userId).getEmail();
        HashMap<String, Object> params = new HashMap<>();
//        String owner = project.getOwner().getEmail();   // надо наверное тут тоже хозяина карточки получить..наверное
//        params.put("owner", owner);
        params.put("projects", projects);
        params.put("user", userEmail);
        return new ModelAndView("projects/list", params);
    }

    @PostMapping(value = "/projects")
    public ModelAndView create_post(@Valid Project project,
                                    BindingResult result,
                                    @CookieValue(value = "userId", defaultValue = "-1") int userId, Model model) {
        if (userId == -1) {
            return new ModelAndView("redirect:login");
        }
        if (result.hasErrors()) {
            return new ModelAndView("projects/list", "formErrors", result.getAllErrors());
        }
        User user = UserService.getById(userId);
        // TODO: catch integrity error - not unique project name
        project = ProjectService.create(project, user);
        HashMap<String, Object> params = new HashMap<>();
        params.put("projectId", project.getId());
        model.addAttribute(user);
        return new ModelAndView("redirect:/projects/{projectId}", params);
    }

    @GetMapping("/projects/{projectId}")
    public ModelAndView view_get(@PathVariable("projectId") Project project,
                                 @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        if (userId == -1) {
            return new ModelAndView("redirect:login");
        }
        // TODO: check that user has access to the project
        Iterable<Task> tasks = project.getTasks();
        HashMap<String, Object> params = new HashMap<>();

        // TODO: add Owner, Contributors on the page
        String owner = project.getOwner().getEmail();
        params.put("owner", owner);
        Iterable<User> users = project.getUsers();
        params.put("usersProject", users);
        String userEmail = UserService.getById(userId).getEmail();
        params.put("user", userEmail);
        //params.put("code", project.getCode());
        params.put("project", project);
        params.put("tasks", tasks);
        return new ModelAndView("projects/view", params);
    }

    @PostMapping(value = "/projects/join")
    public ModelAndView join_post(@Valid Project project,
                                  BindingResult result,
                                  @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        // TODO: pass "owner" from frontend;
        if (result.hasErrors()) {
            return new ModelAndView("projects/list", "formErrors", result.getAllErrors());
        }
        User user = UserService.getById(userId);
        project = ProjectService.getByCode(project.getCode());
        project = ProjectService.addUser(project, user);
        return new ModelAndView("redirect:/projects/{projectId}", "projectId", project.getId());
    }

//    @PostMapping(value = "projects/{projectId}/tasks/{taskId}")
//    public ModelAndView delete(@PathVariable("projectId") Project project,
//                               @PathVariable("taskId") Task task,
//                               @PathVariable("commentId") Comment comment,
//                               @CookieValue(value = "userId", defaultValue = "-1") int userId) {
//        Comment comment1 = CommentService.getById(Integer.parseInt(comment.getId().toString()));
//        CommentService.delete(comment1);
//        return new ModelAndView("redirect:/projects/{projectId}/tasks/{taskId}");
//    }
}