package org.launchcode.mediareview.controllers;

import org.launchcode.mediareview.models.User;
import org.launchcode.mediareview.models.data.ReviewDao;
import org.launchcode.mediareview.user.UserDto;
import org.launchcode.mediareview.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@Controller
public class UserController {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public User getLoggedInUser(Principal principal) {
        if (principal != null) {
            return userService.findByUsername(principal.getName());
        }
        return null;
    }

    @GetMapping(value="/register")
    public String displayRegisterForm(Model model) {
        model.addAttribute(new UserDto());
        model.addAttribute("title", "Register");
        return "user/register";
    }

    @PostMapping(value="/register")
    public String registerUserAccount(Model model, @ModelAttribute @Valid UserDto userDto,
                                      Errors errors){
        if (errors.hasErrors()){
            model.addAttribute("title", "Register");
            return "user/register";
        }

        userService.save(userDto);

        return "redirect:/user";
    }

    @GetMapping(value="/login")
    public String displayLogin(Model model, Principal user) {

        if (user != null)
            return "redirect:/user";

        model.addAttribute("title", "Login");

        return "user/login";
    }

    @PostMapping(value="/login")
    public String processLogin(Model model, Principal user) {

        if (user != null)
            return "redirect:/user";

        model.addAttribute("title", "Login");
        model.addAttribute("message", "Username and/or password is invalid");

        return "user/login";
    }

    @RequestMapping(value="user")
    public String index(Model model) {
        model.addAttribute("title", "Review List");
        model.addAttribute("reviews", reviewDao.findAll());

        return "user/index";
    }
}
