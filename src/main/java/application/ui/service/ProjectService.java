package application.ui.service;

import application.ui.entity.Project;
import application.ui.entity.User;
import application.ui.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    public static Project getByCode(String code){
        return projectRepository.findByCode(code).orElse(null);
    }

    public static Iterable<Project> getAll(){
        return projectRepository.findAll();
    }

    public static Iterable<Project> getAllByUserId(Integer userId){
        User user = UserService.getById(userId);
        return user.getProjects();
    }

    public static Project create(Project project, User owner){
        project.setOwner(owner);

        // TODO: don't select all users by "project.getUsers()" and save whole "users",
        //  just insert one row into "project_user".
        //  There should be something like "project.addUser(user)"
        List<User> users = project.getUsers();
        users.add(owner);
        project.setUsers(users);
        project.setCode(generateCode());

        return projectRepository.save(project);
    }

    public static Project addUser(Project project, User user){
        // TODO: don't select all users by "project.getUsers()" and save whole "users",
        //  just insert one row into "project_user".
        //  There should be something like "project.addUser(user)"
        List<User> users = project.getUsers();
        users.add(user);
        project.setUsers(users);

        return projectRepository.save(project);
    }

    private static String generateCode(){
        return randomString(3) + "-" + randomString(3) + "-" + randomString(3);
    }

    private static String randomString(int length){
        int n_chars = 26 + 10;
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++){
            int char_code = random.nextInt(n_chars);
            System.out.println(char_code);
            if (char_code < 26){
                result.append((char)('a' + char_code));
            }
            else{
                result.append((char)('0' + char_code));
            }
        }
        return result.toString();
    }
}
