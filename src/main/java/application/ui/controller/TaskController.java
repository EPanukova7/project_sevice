package application.ui.controller;

import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//@Controller
//@RequestMapping("/{id}")   // project id
public class TaskController {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

//    @RequestMapping
//    public ModelAndView list() {
//        Iterable<Task> tasks = this.taskRepository.findAll();
//        return new ModelAndView("projects/list", "tasks", tasks);
//    }
//
    @RequestMapping("{id}")
    public ModelAndView view(@PathVariable("id") Project project) {
        return new ModelAndView("projects/view", "project", project);
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute Task task) {
        return "projects/form";
    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    public ModelAndView create(@Valid Task task, BindingResult result,
//                               RedirectAttributes redirect) {
//        if (result.hasErrors()) {
//            return new ModelAndView("projects/form_task", "formErrors", result.getAllErrors());
//        }
//        task = this.taskRepository.save(task);
//        redirect.addFlashAttribute("globalTask", "Successfully created a new task");
//        return new ModelAndView("redirect:/{task.taskId}", "task.taskId", task.getTaskId());
//    }

    @RequestMapping("foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }


}
