package com.gameparty.ScriptStore.service;

import com.gameparty.ScriptStore.entity.Genre;
import com.gameparty.ScriptStore.entity.Script;
import com.gameparty.ScriptStore.entity.User;
import com.gameparty.ScriptStore.exception.ScriptNotFoundException;
import com.gameparty.ScriptStore.model.ScriptModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScriptServiceI {
    boolean saveScript(Script scriptForm);

    boolean postScript(Script scriptForm);

    List<ScriptModel> getAllScripts() throws ScriptNotFoundException;

    boolean deleteScript(Long scriptId) throws ScriptNotFoundException;

    Genre[] getAllGenres();

    List<ScriptModel> getPostedScriptList(Integer page, String sortOption, Boolean isDescending, String title);

    User getCurrentAuthUser();
}
