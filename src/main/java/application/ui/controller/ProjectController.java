package application.ui.controller;

import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.entity.User;
import application.ui.repository.ProjectRepository;
import application.ui.service.ProjectService;
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
    public ModelAndView list_get(@ModelAttribute Project project, User user) {
        if (user.getEmail() == null){
            return new ModelAndView("redirect:login");
        }
        Iterable<Project> projects = ProjectService.getAllByUserId(user.getId());
//        Iterable<Project> projects = ProjectService.getAll();
        HashMap<String, Object> params = new HashMap<>();
        params.put("projects", projects);
//        params.put("user", user);
        return new ModelAndView("projects/list", params);
    }

    @PostMapping(value = "/projects")
    public ModelAndView create_post(@Valid Project project,
                                    User user,
                                    BindingResult result,
                                    RedirectAttributes redirect) {
        // TODO: pass "owner" from frontend;
        if (result.hasErrors()) {
            return new ModelAndView("projects/list", "formErrors", result.getAllErrors());
        }
        // TODO: catch integrity error - not unique project name
        project = ProjectService.create(project, user);
        redirect.addFlashAttribute("globalProject", "Successfully created a new project");
        HashMap<String, Object> params = new HashMap<>();
        params.put("projectId", project.getId());
        params.put("user", user);
        return new ModelAndView("redirect:/projects/{projectId}", params);
    }

    @GetMapping("/projects/{projectId}")
    public ModelAndView view_get(@PathVariable("projectId") Project project, User user) {
        // TODO: hide for users
        Iterable<Task> tasks = project.getTasks();
        System.out.println(tasks);
        HashMap<String, Object> params = new HashMap<>();
        params.put("project", project);
        params.put("tasks", tasks);
        params.put("user", user);
        return new ModelAndView("projects/view", params);
    }

    @PostMapping(value = "/projects/join")
    public ModelAndView join_post(@Valid Project project,
                                  User user,
                                  BindingResult result,
                                  RedirectAttributes redirect) {
        // TODO: pass "owner" from frontend;
        if (result.hasErrors()) {
            return new ModelAndView("projects/list", "formErrors", result.getAllErrors());
        }
        project = ProjectService.getByCode(project.getCode());
        project = ProjectService.addUser(project, user);
        redirect.addFlashAttribute("globalProject", "Successfully created a new project");
        HashMap<String, Object> params = new HashMap<>();
        params.put("projectId", project.getId());
        params.put("user", user);
        return new ModelAndView("redirect:/projects/{projectId}", params);
    }
}