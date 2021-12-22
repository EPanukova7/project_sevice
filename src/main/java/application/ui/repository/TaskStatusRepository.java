package application.ui.repository;

import application.ui.entity.TaskStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TaskStatusRepository extends CrudRepository<TaskStatus, Integer> {
    TaskStatus findTaskStatusByName(String name);

    @Transactional
    @Modifying
    @Query(value = "insert ignore into `task_statuses` (`name`) values (:name)", nativeQuery = true)
    void insertIgnore(@Param("name") String name);
}
