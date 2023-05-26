package com.gameparty.ScriptStore.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;

@NoArgsConstructor
@Entity
@Table(name = "scripts")
@Getter
@Setter
public class Script {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Обязательно поле")
    @Size(message = "Не меньше 5 символов", min = 5)
    @Size(message = "Не больше 40 символов" , max = 40)
    @Column(length = 40, nullable = false)
    private String title;

    @NotBlank(message = "Обязательно поле")
    @Size(max = 1_375_000, message = "Сильно вы размахнулись, максимум 1375000 символа")
    @Size(message = "Не меньше 500 символов", min = 500)
    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    @Column(nullable = false)
    private Date datePublication;

    @Column(nullable = false)
    private Boolean posted;

    @Column(nullable = false)
    private Long views = 0L;

    @ElementCollection(targetClass = Genre.class)
    @CollectionTable(name = "script_genre", joinColumns = @JoinColumn(name = "script_id"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<Genre> genres;


    @ManyToOne
    private User author;


    @NotBlank(message = "Обязательно поле")
    @Size(max = 200, message = "Максимум 200 символов")
    @Column(nullable = false)
    private String synopsis;

    @Column(nullable = false)
    private Integer duration;

}
