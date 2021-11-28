package application.ui.repository;

import application.ui.entity.Project;

public interface ProjectRepository {

    Iterable<Project> findAll();

    Project save(Project project);

    Project findProject(Integer id);

}
