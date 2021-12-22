package application.ui.service;

import application.ui.entity.TaskStatus;
import application.ui.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskStatusService {
    private static final int STATUSES_COUNT = 3;
    static private TaskStatusRepository repository;

    @Autowired
    public void setTaskStatusRepository(TaskStatusRepository taskStatusRepository) {
        repository = taskStatusRepository;
    }

    static public Iterable<TaskStatus> getAll() {
        return repository.findAll();
    }

    static public Optional<TaskStatus> getById(int id) {
        return repository.findById(id);
    }

    public static Object getAllById(Integer id) {
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(id);
        int nextId = (id + 1) % STATUSES_COUNT;
        if (nextId == 0) {
            nextId = 1;
        }
        ids.add(nextId);
        return repository.findAllById(ids);
    }
}
