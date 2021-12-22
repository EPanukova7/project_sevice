package application.ui.service;

import application.ui.entity.Project;
import application.ui.entity.User;
import application.ui.repository.ProjectRepository;
import application.ui.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    public Project getById(Integer id){
        return projectRepository.findById(id).orElse(null);
    }

    public Project getByCode(String code){
        return projectRepository.findByCode(code).orElse(null);
    }

    public Iterable<Project> getAll(){
        return projectRepository.findAll();
    }

    public Project create(Project project, User owner){
        project.setOwner(owner);
        project.addUser(owner);
        project.setCode(generateCode());
        return projectRepository.save(project);
    }

    public void delete(Project project){
        projectRepository.delete(project);
    }

    public Project addUser(Project project, User user){
        project.addUser(user);
        return projectRepository.save(project);
    }

    private String generateCode(){
        return randomString(3) + "-" + randomString(3) + "-" + randomString(3);
    }

    private String randomString(int length){
        int n_chars = 26 + 10;
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++){
            int char_code = random.nextInt(n_chars);
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
