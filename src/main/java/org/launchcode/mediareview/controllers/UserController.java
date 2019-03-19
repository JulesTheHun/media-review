package org.launchcode.mediareview.controllers;

import org.launchcode.mediareview.models.User;
import org.launchcode.mediareview.models.data.ReviewDao;
import org.launchcode.mediareview.user.AccountExistsException;
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
public class UserController extends BaseController {

    @Autowired
    private ReviewDao reviewDao;

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

        try {
            userService.save(userDto);
        } catch (AccountExistsException e){
            if (e.doesEmailExist()) {
                errors.rejectValue("email", "email.alreadyexists", e.emailExistsMessage());
            }
            if (e.doesUsernameExist()) {
                errors.rejectValue("username", "username.alreadyexists", e.usernameExistsMessage());
            }
            return "user/register";
        }

        return "redirect:/user";
    }

    @GetMapping(value="/login")
    public String displayLogin(Model model, Principal user, String error, String logout) {

        if (user != null) {
            return "redirect:/user";
        }

        if (error != null) {
            model.addAttribute("message", "Username and/or password is invalid");
        }
        if (logout !=null) {
            model.addAttribute("message", "Logout successful!");
        }

        model.addAttribute("title", "Login");
        return "user/login";
    }

    @RequestMapping(value="user")
    public String index(Model model, Principal principal) {

        User user = getLoggedInUser(principal);

        model.addAttribute("title", user.getUsername() + "'s Reviews");
        model.addAttribute("reviews", user.getReviews());

        return "user/index";
    }
}
