package com.gameparty.ScriptStore.controller;

import com.gameparty.ScriptStore.exception.UserNotFoundException;
import com.gameparty.ScriptStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String userList(Model model) throws UserNotFoundException {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "admin";
    }

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) throws UserNotFoundException {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/users/{page}")
    public String  gtUser(@PathVariable("userId") Integer page, Model model) {
        model.addAttribute("allUsers", userService.getUserList(page));
        return "adminUsers";
    }
}