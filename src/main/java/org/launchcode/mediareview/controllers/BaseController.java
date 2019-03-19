package org.launchcode.mediareview.controllers;

import org.launchcode.mediareview.models.User;
import org.launchcode.mediareview.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

public abstract class BaseController {
    @Autowired
    UserService userService;

    @ModelAttribute("user")
    public User getLoggedInUser(Principal principal) {
        if (principal != null) {
            return userService.findByUsername(principal.getName());
        }
        return null;
    }
}

