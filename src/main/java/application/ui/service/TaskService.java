package application.ui.service;

import application.ui.entity.Task;
import application.ui.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskService implements TaskRepository {
    // для работы с базой данный

    private static AtomicInteger counter = new AtomicInteger();

    private final ConcurrentMap<Integer, Task> tasks = new ConcurrentHashMap<Integer, Task>(); //

    @Override
    public Iterable<Task> findAll() {
        return this.tasks.values();
    }

    @Override
    public Iterable<Task> findTasksByProjectId(int projectId) {
        ArrayList<Task> result = new ArrayList<Task>();
        for (Task task : tasks.values()) {
            if (task.getProjectId().equals(projectId)) {
                result.add(task);
            }
        }
        return result;
    }

    @Override
    public Task save(Task task) {
        Integer id = task.getId();
        if (id == null) {
            id = counter.incrementAndGet();
            task.setId(id);
        }
        this.tasks.put(id, task);
        return task;
    }

    @Override
    public Task findTask(Integer id) {
        return this.tasks.get(id);
    }

}
