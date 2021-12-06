package application.ui.service;

import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.entity.User;
import application.ui.repository.TaskRepository;
import application.ui.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        UserService.userRepository = userRepository;
    }

    public static User getById(Integer id){
        return userRepository.findById(id).orElse(null);
    }

    public static Iterable<User> getAllByProject_id(Integer projectId){
        Project project = ProjectService.getById(projectId);
        return project.getUsers();
    }

    public static User create(User user){
        return userRepository.save(user);
    }
}
