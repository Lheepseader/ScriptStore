package com.gameparty.ScriptStore.controller;

import com.gameparty.ScriptStore.exception.PasswordsNotMatchException;
import com.gameparty.ScriptStore.exception.UserAlreadyExistException;
import com.gameparty.ScriptStore.service.ScriptService;
import com.gameparty.ScriptStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;


    @Autowired
    private ScriptService scriptService;

    @GetMapping("/registration")
    public String showRegistrationForm(@AuthenticationPrincipal User user, Model model) {
        scriptService.setUsernameOnCurrentPage(model, user);
        model.addAttribute("user", new com.gameparty.ScriptStore.entity.User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@Valid com.gameparty.ScriptStore.entity.User user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "registration";
        }

        try {
            userService.registration(user);
        } catch (UserAlreadyExistException | PasswordsNotMatchException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("user", user);
            return "registration";
        }


        return "redirect:/login";
    }


}
