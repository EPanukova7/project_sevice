package application.ui.repository;

import application.ui.entity.User;
import org.springframework.data.repository.CrudRepository;

import application.ui.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, Integer>{
    Optional<Project> findByCode(String code);
}
