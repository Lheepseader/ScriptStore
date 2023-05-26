package com.gameparty.ScriptStore.controller;

import com.gameparty.ScriptStore.entity.Script;
import com.gameparty.ScriptStore.repository.ScriptRepository;
import com.gameparty.ScriptStore.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class ScriptController {

    @Autowired
    private ScriptService scriptService;
    @Autowired
    private ScriptRepository scriptRepository;

    @GetMapping("/")
    public String index(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                        @RequestParam(value = "sort_option", required = false, defaultValue = "id") String sortOption,
                        @RequestParam(value = "descending", required = false, defaultValue = "false") Boolean isDescending,
                        @RequestParam(value = "search", required = false, defaultValue = "") String title,
                        @AuthenticationPrincipal User user,
                        Model model) {

        scriptService.setUsernameOnCurrentPage(model, user);
        int maxPage;
        if (title.equals("")) {
            maxPage = scriptService.getMaxPage();
        } else {
            maxPage = scriptService.getMaxPageByTitle(title);
        }
        model.addAttribute("scripts", scriptService.getPostedScriptList(page, sortOption, isDescending, title));
        model.addAttribute("maxPage", maxPage);
        model.addAttribute("page", page + 1);
        return "index";
    }



    @GetMapping("/create")
    public String showCreateForm(@AuthenticationPrincipal User user, Model model) {
        scriptService.setUsernameOnCurrentPage(model, user);
        model.addAttribute("script", new Script());
        model.addAttribute("genresList", scriptService.getAllGenres());
        return "create";
    }


    @PostMapping(value = "/create", params = "post")
    public String postScript(@ModelAttribute("script") @Valid Script script,
                               BindingResult result,
                               Model model) {

        if (result.hasErrors()) {
            model.addAttribute("script", script);
            model.addAttribute("genresList", scriptService.getAllGenres());
            return "create";
        }

        scriptService.postScript(script);

        return "redirect:/";
    }

    @PostMapping(value = "/create", params = "save")
    public String saveScript(@ModelAttribute("script") @Valid Script script,
                               BindingResult result,
                               Model model) {

        if (result.hasErrors()) {
            model.addAttribute("script", script);
            return "create";
        }

        scriptService.saveScript(script);

        return "redirect:/";
    }


    @GetMapping(value = "/script/{id}")
    public String showScript(@AuthenticationPrincipal User user ,@PathVariable Long id, Model model) {
        scriptService.setUsernameOnCurrentPage(model, user);
        model.addAttribute("script", scriptService.getScriptModelById(id));
        return "script";
    }

}

