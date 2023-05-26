package com.gameparty.ScriptStore.service;


import com.gameparty.ScriptStore.entity.Genre;
import com.gameparty.ScriptStore.entity.Script;
import com.gameparty.ScriptStore.entity.User;
import com.gameparty.ScriptStore.exception.ScriptNotFoundException;
import com.gameparty.ScriptStore.model.ScriptModel;
import com.gameparty.ScriptStore.repository.ScriptRepository;
import com.gameparty.ScriptStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ScriptService implements ScriptServiceI {

    final int AVG_SING_PER_MINUTE = 863;

    @Autowired
    private ScriptRepository scriptRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public boolean saveScript(Script scriptForm) {
        scriptForm.setPosted(false);
        scriptForm.setAuthor(getCurrentAuthUser());
        scriptForm.setDuration(scriptForm.getContent().length() / AVG_SING_PER_MINUTE);
        scriptRepository.save(scriptForm);
        return true;
    }

    @Override
    public boolean postScript(Script scriptForm) {
        scriptForm.setPosted(true);
        scriptForm.setAuthor(getCurrentAuthUser());
        scriptForm.setDuration(scriptForm.getContent().length() / AVG_SING_PER_MINUTE);
        scriptRepository.save(scriptForm);
        return true;
    }

    @Override
    public User getCurrentAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).get();
        return user;
    }

    @Override
    public List<ScriptModel> getAllScripts() throws ScriptNotFoundException {
        List<Script> scripts = scriptRepository.findAll();
        if (scripts.isEmpty()) {
            throw new ScriptNotFoundException();
        }
        List<ScriptModel> scriptModels = scripts.stream().map(x -> ScriptModel.toModel(x)).collect(Collectors.toList());
        return scriptModels;
    }


    @Override
    public boolean deleteScript(Long scriptId) throws ScriptNotFoundException {
        Script script = scriptRepository.findById(scriptId).orElseThrow(() -> new ScriptNotFoundException());
        return true;
    }

    @Override
    public Genre[] getAllGenres() {
        Genre[] genres = Genre.values();
        return genres;
    }

    @Override
    public List<ScriptModel> getPostedScriptList(Integer page, String sortOption, Boolean isDescending, String title) {
        final boolean posted = true;
        final int size = 10;


        Pageable pageable;

        if (isDescending) {
            pageable = PageRequest.of(page, size, Sort.by(sortOption).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortOption));
        }

        List<Script> scripts = scriptRepository.findByTitleContainsIgnoreCaseAndPosted(title, posted, pageable);
        List<ScriptModel> scriptModels = new ArrayList<>();
        for (Script script : scripts) {
            ScriptModel model = ScriptModel.toModel(script);
            model.setContent(model.getContent().substring(0, 150) + "...");
            scriptModels.add(model);
        }
        return scriptModels;
    }

    public int getMaxPage() {
        final float size = 10;
        float allScripts = scriptRepository.findByPostedTrue().size();

        float avgPages = allScripts / size;
        int maxPage = (int)Math.ceil(avgPages);
        return maxPage;
    }

    public int getMaxPageByTitle(String title) {
        boolean posted = true;
        final float size = 10;
        float allScripts = scriptRepository.findByTitleContainsIgnoreCaseAndPosted(title, posted).size();

        float avgPages = allScripts / size;

        int maxPage = (int) Math.ceil(avgPages);

        return maxPage;
    }


    public void setUsernameOnCurrentPage(Model model, org.springframework.security.core.userdetails.User user) {
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        } else {
            model.addAttribute("username", "Анонимус");
        }
    }

    public ScriptModel getScriptModelById(Long id) {
        Optional<Script> script = scriptRepository.findById(id);
         if (script.isPresent()) {
             return ScriptModel.toModel(script.get());
         } else {
             return ScriptModel.toModel(scriptRepository.findById(1l).get());
         }

    }
}
