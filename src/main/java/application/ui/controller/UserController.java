package application.ui.controller;

import application.ui.entity.User;
import application.ui.repository.UserRepository;
import application.ui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;

@Controller
public class UserController {
    @PostMapping(value = "/login")
    public ModelAndView login_post(@Valid User user, BindingResult result,
                                   HttpServletResponse response) {
        if (result.hasErrors()) {
            return new ModelAndView("users/login", "formErrors", result.getAllErrors());
        }
        User dbUser = UserService.getByEmail(user.getEmail());
        // TODO: hash password
        if (dbUser == null) {
            dbUser = UserService.create(user);
        } else if (!dbUser.getPassword().equals(user.getPassword())) {
            return new ModelAndView("users/login", "error", "Wrong password");
        }
        Cookie cookie = new Cookie("userId", dbUser.getId().toString());
        cookie.setMaxAge(3600);
        cookie.setSecure(true);
        response.addCookie(cookie);
        return new ModelAndView("redirect:/projects", "user", dbUser);
    }

    @GetMapping(value = "/login")
    public ModelAndView login_get(@ModelAttribute User user) {
        return new ModelAndView("users/login");
    }

    private String hashPassword(String password) {
        // TODO
        return "";
    }
}
