package com.gameparty.ScriptStore.model;

import com.gameparty.ScriptStore.entity.Genre;
import com.gameparty.ScriptStore.entity.Script;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScriptModel {
    private Long id;
    private String title;
    private String content;
    private String datePublication;
    private Boolean posted;
    private Long views;
    private Collection<Genre> genres;
    private String author;
    private String synopsis;
    private Integer duration;

    public static ScriptModel toModel(Script script) {
        ScriptModel model = new ScriptModel();
        model.setId(script.getId());
        model.setTitle(script.getTitle());
        model.setContent(script.getContent());

        DateFormat df = new SimpleDateFormat("dd MMM yyy");
        model.setDatePublication(df.format(script.getDatePublication().getTime()));
        model.setPosted(script.getPosted());


        model.setViews(script.getViews());
        model.setGenres(script.getGenres());
        model.setAuthor(script.getAuthor().getUsername());
        model.setSynopsis(script.getSynopsis());
        model.setDuration(script.getDuration());
        return model;
    }

    public String getGenresAsString() {
        return String.join(", ", genres.stream().map(Genre::getTitle).toList());
    }



}
