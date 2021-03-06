package application.ui.service;

import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.entity.User;
import application.ui.repository.ProjectRepository;
import application.ui.repository.TaskRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public static Iterable<Task> getAllByProject_id(Integer id){
        Project project = ProjectService.getById(id);
        return project.getTasks();
    }

    public static Task create(Project project, Task task){
        task.setProject(project);
        return taskRepository.save(task);
    }
}
