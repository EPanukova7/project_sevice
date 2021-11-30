package application.ui.repository;
import application.ui.entity.Task;

public interface TaskRepository {
    Iterable<Task> findAll();

    Iterable<Task> findTasksByProjectId(int projectId);

    Task save(Task project);

    Task findTask(Integer id);
}
