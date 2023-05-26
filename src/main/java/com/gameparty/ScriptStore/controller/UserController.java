package com.gameparty.ScriptStore.controller;

import com.gameparty.ScriptStore.exception.UserNotFoundException;
import com.gameparty.ScriptStore.model.UserModel;
import com.gameparty.ScriptStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping("/user")
    public ModelAndView getOneUser(@RequestParam String username) throws UserNotFoundException {
        ModelAndView mav = new ModelAndView("user");
        UserModel userModel = userService.getUserByUsername(username);
        mav.addObject("username", userModel.getUsername());

        return mav;
    }


    @GetMapping("/greeting")
    public String getUsername(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        } else {
            model.addAttribute("username", "ты кто такой?");
        }
        return "greeting";
    }
}
