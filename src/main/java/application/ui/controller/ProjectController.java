package application.ui.controller;

import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.repository.ProjectRepository;
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
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping(value = "/projects")
    public ModelAndView list(@ModelAttribute Project project) {
        Iterable<Project> projects = this.projectRepository.findAll();
        return new ModelAndView( "projects/list", "projects", projects);
    }

    @PostMapping(value = "/projects")
    public ModelAndView create(@Valid Project project, BindingResult result,
                               RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("projects/list", "formErrors", result.getAllErrors());
        }
        project = this.projectRepository.save(project);
        redirect.addFlashAttribute("globalProject", "Successfully created a new project");
        return new ModelAndView("redirect:/projects/{project.id}", "project.id", project.getId());
    }

    @GetMapping("/projects/{project_id}")
    public ModelAndView view(@PathVariable("project_id") Project project) {
        Iterable<Task> tasks = project.getTasks();
        System.out.println(tasks);
        HashMap<String, Object> params = new HashMap<>();
        params.put("project", project);
        params.put("tasks", tasks);
        return new ModelAndView("projects/view", params);
    }
}