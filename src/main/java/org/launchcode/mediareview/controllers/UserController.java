package org.launchcode.mediareview.controllers;

import org.launchcode.mediareview.models.User;
import org.launchcode.mediareview.models.data.ReviewDao;
import org.launchcode.mediareview.user.UserDto;
import org.launchcode.mediareview.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        if (principal != null)
            return userService.findByUsername(principal.getName());
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

        return "redirect:/user/" + userService.findByUsername(userDto.getUsername()).getId();
    }

    @GetMapping(value="/login")
    public String login(Model model, Principal user, String error) {

        if (user != null)
            return "redirect:/user/" + userService.findByUsername(user.getName()).getId();

        if (error != null)
            model.addAttribute("message", "Your username and/or password is invalid");

        return "login";
    }

    @RequestMapping(value="/user/{userId}")
    public String index(Model model) {
        model.addAttribute("title", "Review List");
        model.addAttribute("reviews", reviewDao.findAll());

        return "review/index";
    }
}
