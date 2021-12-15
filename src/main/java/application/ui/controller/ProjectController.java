package application.ui.controller;

import application.ui.Validation;
import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.entity.User;
import application.ui.repository.ProjectRepository;
import application.ui.service.ProjectService;
import application.ui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
public class ProjectController {
    @GetMapping(value = "/projects")
    public ModelAndView list_get(@ModelAttribute Project project, @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        if (userId == -1) {
            return new ModelAndView("redirect:login");
        }
        Iterable<Project> projects = ProjectService.getAllByUserId(userId);
        return new ModelAndView("projects/list", "projects", projects);
    }

    @PostMapping(value = "/projects")
    public ModelAndView create_post(@Valid Project project,
                                    BindingResult result,
                                    @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        if (userId == -1) {
            return new ModelAndView("redirect:login");
        }
        if (result.hasErrors()) {
            return new ModelAndView("projects/list", "formErrors", result.getAllErrors());
        }
        if (!Validation.isCorrectName(project.getName())){
            return new ModelAndView("projects/list", "error", "Incorrect project name. Use a-zA-Z0-9_-");
        }
        User user = UserService.getById(userId);
        // TODO: catch integrity error - not unique project name
        project = ProjectService.create(project, user);
        HashMap<String, Object> params = new HashMap<>();
        params.put("projectId", project.getId());
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
}