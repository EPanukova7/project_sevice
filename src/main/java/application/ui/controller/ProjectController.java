package application.ui.controller;

import application.ui.entity.Project;
import application.ui.repository.ProjectRepository;
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

@Controller
@RequestMapping("/")
public class ProjectController {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @RequestMapping
    public ModelAndView list() {
        Iterable<Project> projects = this.projectRepository.findAll();
        return new ModelAndView("projects/list", "projects", projects);
    }

    @RequestMapping("{id}")
    public ModelAndView view(@PathVariable("id") Project project) {
        return new ModelAndView("projects/view", "project", project);
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute Project project) {
        return "projects/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@Valid Project project, BindingResult result,
                               RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("projects/form", "formErrors", result.getAllErrors());
        }
        project = this.projectRepository.save(project);
        redirect.addFlashAttribute("globalProject", "Successfully created a new project");
        return new ModelAndView("redirect:/{project.id}", "project.id", project.getId());
    }

    @RequestMapping("foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }

}