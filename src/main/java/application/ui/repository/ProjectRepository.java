package application.ui.repository;

import org.springframework.data.repository.CrudRepository;

import application.ui.entity.Project;

public interface ProjectRepository extends CrudRepository<Project, Integer>{

}
