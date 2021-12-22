package application.ui.repository;
import application.ui.entity.Project;
import application.ui.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    ArrayList<Task> findAllByExecutorIdAndProjectId(Integer executorId, Integer projectId);
}
