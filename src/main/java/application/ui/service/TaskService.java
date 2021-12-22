package application.ui.service;

import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.entity.TaskStatus;
import application.ui.entity.User;
import application.ui.repository.ProjectRepository;
import application.ui.repository.TaskRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private static TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository){
        TaskService.taskRepository = taskRepository;
    }

    public static Task getById(Integer id){
        return taskRepository.findById(id).orElse(null);
    }

    public static Task create(Project project, Task task, User user){
        task.setProject(project);
        task.setOwner(user);
        return taskRepository.save(task);   // ругался на сохранение таксов
    }

    public static ArrayList<Task> getAllByExecutorIdAndProjectId(Integer executorId, Integer projectId){
        return taskRepository.findAllByExecutorIdAndProjectId(executorId, projectId);
    }

    public static void setExecutor(Task task, User user) {   // status??
        task.setExecutor(user);
    }

}
