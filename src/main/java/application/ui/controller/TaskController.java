package application.ui.controller;

import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.repository.TaskRepository;
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
@RequestMapping("/project/{id}")   // project id
public class TaskController {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView list(@PathVariable("id") Project project) {
        Iterable<Task> tasks = this.taskRepository.findAll();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("task", new Task());
        map.put("tasks", tasks);
        map.put("project", project);
        return new ModelAndView("tasks/add", map);
    }

    @RequestMapping("{id}")
    public ModelAndView view(@PathVariable("id") Task task) {
        return new ModelAndView("projects/view", "task", task);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@Valid Task task, BindingResult result,
                               RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("tasks/add", "formErrors", result.getAllErrors());
        }
        task = this.taskRepository.save(task);
        redirect.addFlashAttribute("globalTask", "Successfully created a new task");
        return new ModelAndView("redirect:/project/{id}/{task.id}", "task.id", task.getId());
    }
}
