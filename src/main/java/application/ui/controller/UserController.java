package application.ui.controller;

import application.ui.entity.User;
import application.ui.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    UserRepository repository;

    @PostMapping(value = "/login")
    public ModelAndView loginOrRegister(@Valid User user,
                                        BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("users/login", "formErrors", result.getAllErrors());
        }
        User dbUser = repository.findUserByEmail(user.getEmail());
        if (dbUser == null) {
            repository.save(user);
        }
        return new ModelAndView("redirect:/projects");
    }

    @GetMapping(value = "/login")
    public ModelAndView login(@ModelAttribute User user) {
        return new ModelAndView("users/login");
    }
}
