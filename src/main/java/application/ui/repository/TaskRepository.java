package application.ui.repository;
import application.ui.entity.Project;
import application.ui.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    //Iterable<Task> findAllByExecutorIdAAndProjectId(Integer executorId, Integer projectId);
}
