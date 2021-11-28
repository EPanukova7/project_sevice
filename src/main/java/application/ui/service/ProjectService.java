package application.ui.service;

import application.ui.entity.Project;
import application.ui.repository.ProjectRepository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ProjectService implements ProjectRepository {
    // DataBase realisation: queries and connection
    // для работы с базой данный

    private static AtomicInteger counter = new AtomicInteger();

    private final ConcurrentMap<Integer, Project> projects = new ConcurrentHashMap<Integer, Project>(); //

    @Override
    public Iterable<Project> findAll() {
        return this.projects.values();
    }

    @Override
    public Project save(Project project) {
        Integer id = project.getId();
        if (id == null) {
            id = counter.incrementAndGet();
            project.setId(id);
        }
        this.projects.put(id, project);
        return project;
    }

    @Override
    public Project findProject(Integer id) {
        return this.projects.get(id);
    }

}
