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
import java.util.regex.Pattern;

@Controller
public class UserController {
    @PostMapping(value = "/login")
    public ModelAndView login_post(@Valid User user, BindingResult result,
                                   HttpServletResponse response) {
        if (result.hasErrors()) {
            return new ModelAndView("users/login", "formErrors", result.getAllErrors());
        }
        if (!Validation.isCorrectEmail(user.getEmail())){
            System.out.println("Incorrect email");
            System.out.println(user.getEmail());
            result.addError(new FieldError("user", "email", "Incorrect email"));
            return new ModelAndView("users/login", "formErrors", result.getAllErrors());
        }
        if (!Validation.isCorrectPassword(user.getPassword())){
            result.addError(new FieldError("user", "password",
                    "Incorrect password format. Length should be between 6 and 64"));
            return new ModelAndView("users/login", "formErrors", result.getAllErrors());
        }
        User dbUser = UserService.getByEmail(user.getEmail());
        // TODO: hash password
        if (dbUser == null) {
            UserService.create(user);
            dbUser = user;
        } else if (!dbUser.getPassword().equals(user.getPassword())) {
            result.addError(new FieldError("user", "password", "Wrong password"));
            return new ModelAndView("users/login", "formErrors", result.getAllErrors());
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

    private static String hashPassword(String password) {
        // TODO
        return "";
    }

    private static boolean isCorrectEmail(String url){
        String pattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$";
        return patternMatches(url, pattern);
    }

    private static boolean patternMatches(String string, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(string)
                .matches();
    }
}
