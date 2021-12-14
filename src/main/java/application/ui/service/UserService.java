package application.ui.service;

import application.ui.entity.Project;
import application.ui.entity.User;
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

    public static User getByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public static Iterable<User> getAllByProjectId(Integer projectId){
        Project project = ProjectService.getById(projectId);
        return project.getUsers();
    }

    public static User create(User user){

        return userRepository.save(user);
    }

    public static void delete(User user) {
        userRepository.delete(user);
    }
}
