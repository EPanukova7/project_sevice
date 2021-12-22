package application.ui.repository;

import application.ui.entity.TaskStatus;
import org.springframework.data.repository.CrudRepository;

public interface TaskStatusRepository extends CrudRepository<TaskStatus, Integer> {
}
