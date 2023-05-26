package com.gameparty.ScriptStore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Genre {
    ACTION("Боевик"),
    ADVENTURE("Приключение"),
    DRAMA("Драма"),
    THRILLER("Триллер"),
    CRIME ("Криминальна"),
    DETECTIVE ("Детектив"),
    DISTOPIA ("Антиутопия"),
    CYBERPUNK ("Киберпанк"),
    FANTASY ("Фэнтези"),
    ROMANTIC ("Роман"),
    WESTERN ("Вестерн"),
    HORROR ("Ужасы");

    final private String title;

}
