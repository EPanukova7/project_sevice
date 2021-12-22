package application.ui.controller;

import application.ui.Validation;
import application.ui.entity.Project;
import application.ui.entity.User;
import application.ui.service.ProjectService;
import application.ui.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
public class ProjectController {
    final ProjectService projectService;
    final UserService userService;

    public ProjectController(ProjectService projectService,
                             UserService userService){
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public ModelAndView index() {
        return new ModelAndView("redirect:/projects");
    }

    @GetMapping(value = "/projects")
    public ModelAndView listGet(@ModelAttribute Project project,
                                @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        if (userId == -1) {
            return new ModelAndView("redirect:/login");
        }
        User user = userService.getById(userId);
        Iterable<Project> projects = user.getProjects();
        HashMap<String, Object> params = new HashMap<>();
        params.put("projects", projects);
        params.put("user", user);
        return new ModelAndView("projects/list", params);
    }

    @PostMapping(value = "/projects")
    public ModelAndView createPost(@Valid Project project,
                                    BindingResult result,
                                    @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        if (userId == -1) {
            return new ModelAndView("redirect:/login");
        }
        if (!Validation.isCorrectName(project.getName())){
            result.addError(new FieldError("project", "name", "Incorrect project name. Use a-zA-Z0-9_-"));
        }
        if (result.hasErrors()) {
            return new ModelAndView("projects/list", "formErrors", result.getAllErrors());
        }
        User user = userService.getById(userId);
        // TODO: catch integrity error - not unique project name
        project = projectService.create(project, user);
        return new ModelAndView("redirect:/projects/{projectId}", "projectId", project.getId());
    }

    @PostMapping(value = "/projects/join")
    public ModelAndView joinPost(@Valid Project project,
                                 BindingResult result,
                                 @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        if (userId == -1) {
            return new ModelAndView("redirect:/login");
        }
        if (result.hasErrors()) {
            return new ModelAndView("projects/list", "formErrors", result.getAllErrors());
        }
        User user = userService.getById(userId);
        project = projectService.getByCode(project.getCode());
        project = projectService.addUser(project, user);
        return new ModelAndView("redirect:/projects/{projectId}", "projectId", project.getId());
    }

    @GetMapping("/projects/{projectId}")
    public ModelAndView viewGet(@PathVariable("projectId") Project project,
                                @CookieValue(value = "userId", defaultValue = "-1") int userId) {
        if (userId == -1) {
            return new ModelAndView("redirect:/login");
        }
        User user = userService.getById(userId);
        // TODO: check that user has access to the project

        HashMap<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("project", project);
        params.put("projectOwner", project.getOwner());
        params.put("projectUsers", project.getUsers());
        params.put("projectTasks", project.getTasks());
        return new ModelAndView("projects/view", params);
    }

    @DeleteMapping(value = "projects/{projectId}")
    public ModelAndView delete(@PathVariable("projectId") Project project,
                               @CookieValue(value = "userId", defaultValue = "-1") int userId,
                               RedirectAttributes redirect) {
        User user = userService.getById(userId);
        if (!user.getId().equals(project.getOwner().getId())){
            redirect.addFlashAttribute("errorMessage", "Permission denied");
        }
        projectService.delete(project);
        return new ModelAndView("redirect:/projects");
    }
}