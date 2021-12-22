package application.ui.service;

import application.ui.entity.Project;
import application.ui.entity.User;
import application.ui.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getById(Integer id){
        return userRepository.findById(id).orElse(null);
    }

    public User getByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public User create(User user){
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
