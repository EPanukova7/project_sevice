package application.ui.controller;

import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.repository.ProjectRepository;
import application.ui.repository.TaskRepository;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
@RequestMapping("/")
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @RequestMapping("project/{id}")
    public ModelAndView view(@PathVariable("id") Project project) {
        Iterable<Task> tasks = taskRepository.findTasksByProjectId(project.getId());
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("project", project);
        params.put("tasks", tasks);
        return new ModelAndView("projects/view", params);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView createForm(@ModelAttribute Project project) {
        Iterable<Project> projects = this.projectRepository.findAll();
        return new ModelAndView( "projects/form", "projects", projects);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@Valid Project project, BindingResult result,
                               RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("projects/form", "formErrors", result.getAllErrors());
        }
        project = this.projectRepository.save(project);
        redirect.addFlashAttribute("globalProject", "Successfully created a new project");
        return new ModelAndView("redirect:/project/{project.id}", "project.id", project.getId());
    }
}