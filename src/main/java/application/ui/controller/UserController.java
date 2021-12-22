package application.ui.controller;

import application.ui.Validation;
import application.ui.entity.User;
import application.ui.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class UserController {
    final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public ModelAndView loginGet(@ModelAttribute User user) {
        return new ModelAndView("users/login");
    }

    @PostMapping(value = "/login")
    public ModelAndView loginPost(@Valid User user, BindingResult result,
                                   HttpServletResponse response) {
        if (!Validation.isCorrectEmail(user.getEmail())){
            result.addError(new FieldError("user", "email", "Incorrect email"));
        }
        if (!Validation.isCorrectPassword(user.getPassword())){
            result.addError(new FieldError("user", "password",
                    "Incorrect password format. Length should be between 6 and 64"));
        }
        if (result.hasErrors()) {
            return new ModelAndView("users/login", "formErrors", result.getAllErrors());
        }
        User dbUser = userService.getByEmail(user.getEmail());
        // TODO: hash password
        if (dbUser == null) {
            userService.create(user);
            dbUser = user;
        } else if (!dbUser.getPassword().equals(user.getPassword())) {
            result.addError(new FieldError("user", "password", "Wrong password"));
            return new ModelAndView("users/login", "formErrors", result.getAllErrors());
        }
        Cookie cookie = new Cookie("userId", dbUser.getId().toString());
        cookie.setMaxAge(3600);
        cookie.setSecure(true);
        response.addCookie(cookie);
        return new ModelAndView("redirect:/projects");
    }
}
