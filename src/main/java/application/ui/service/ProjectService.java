package application.ui.service;

import application.ui.entity.Project;
import application.ui.entity.User;
import application.ui.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private static ProjectRepository projectRepository;

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository){
        ProjectService.projectRepository = projectRepository;
    }

    public static Project getById(Integer id){
        return projectRepository.findById(id).orElse(null);
    }

    public static Iterable<Project> getAll(){
        return projectRepository.findAll();
    }

//    public static Project create(Project project, User owner){
//        project.setOwner(owner);
//
//        // TODO: don't select all users by "project.getUsers()" and save whole "users",
//        //  just insert one row into "project_user".
//        //  There should be something like "project.addUser(user)"
//        List<User> users = project.getUsers();
//        users.add(owner);
//        project.setUsers(users);
//
//        return projectRepository.save(project);
//    }

//    public static Project addUser(Project project, User user){
//        // TODO: don't select all users by "project.getUsers()" and save whole "users",
//        //  just insert one row into "project_user".
//        //  There should be something like "project.addUser(user)"
//        List<User> users = project.getUsers();
//        users.add(user);
//        project.setUsers(users);
//
//        return projectRepository.save(project);
//    }

}
