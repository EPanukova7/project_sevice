package application.ui.service;

import application.ui.entity.*;
import application.ui.repository.CommentRepository;
import application.ui.repository.TaskRepository;
import application.ui.repository.TaskStatusRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final CommentRepository commentRepository;
    @Getter
    private final String[] statusNames = {"К выполнению", "В процессе", "Выполнено"};
    private final TaskStatus defaultTaskStatus;

    public TaskService(TaskRepository taskRepository,
                       TaskStatusRepository taskStatusRepository,
                       CommentRepository commentRepository){
        this.taskRepository = taskRepository;
        this.taskStatusRepository = taskStatusRepository;
        this.commentRepository = commentRepository;

        for (String statusName : statusNames){
            this.taskStatusRepository.insertIgnore(statusName);
        }
        this.defaultTaskStatus = taskStatusRepository.findTaskStatusByName(statusNames[0]);
    }

    public Task getById(Integer id){
        return taskRepository.findById(id).orElse(null);
    }

    public TaskStatus getStatusById(Integer id){
        return taskStatusRepository.findById(id).orElse(null);
    }

    public Task create(Project project, Task task, User owner){
        task.setProject(project);
        task.setExecutor(owner);
        task.setStatus(this.defaultTaskStatus);
        return taskRepository.save(task);
    }

    public void updateExecutor(Task task, User executor){
        System.out.println(executor.getEmail());
        task.setExecutor(executor);
        taskRepository.save(task);
    }

    public void updateStatus(Task task, TaskStatus status){
        task.setStatus(status);
        taskRepository.save(task);
    }

    public void createComment(Task task, Comment comment, User user){
        comment.setOwner(user);
        comment.setTask(task);
        task.addComment(comment);
        taskRepository.save(task);
        commentRepository.save(comment);
    }

    public void deleteComment(Comment comment){
        commentRepository.delete(comment);
    }

    public Iterable<TaskStatus> getAllStatuses(){
        return taskStatusRepository.findAll();
    }

    public Iterable<TaskStatus> getPossibleStatusesByTaskStatus(TaskStatus status) {
        List<Integer> ids = new ArrayList<>();
        ids.add(status.getId());
        ids.add(status.getId() % statusNames.length + 1);
        return taskStatusRepository.findAllById(ids);
    }

//    public static Iterable<Task> getAllByExecutorIdAAndProjectId(Integer executorId, Integer projectId){
//        return taskRepository.findAllByExecutorIdAAndProjectId(executorId, projectId);
//    }
}
