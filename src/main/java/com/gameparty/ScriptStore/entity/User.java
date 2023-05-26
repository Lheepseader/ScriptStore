package com.gameparty.ScriptStore.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(message = "Не больше 20 символов", max = 20)
    @Size(message = "Не меньше 3 символов", min = 3)
    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Size(message = "Не больше 60 символов", max = 60)
    @Size(message = "Не меньше 5 символов", min = 5)
    @Column(length = 60, nullable = false)
    private String password;

    @Transient
    @Column(nullable = false)
    private String matchingPassword;

    @Size(message = "Не больше 60 символов", max = 60)
    @Column(length = 60, nullable = false, unique = true)
    private String email;

    @CreationTimestamp
    @Column(nullable = false)
    private Date date;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    @Column(nullable = false)
    private List<Script> scripts;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    @Column(nullable = false)
    private List<Role> roles = new ArrayList<>();

    @Column(name = "reset_password_token")
    private  String resetPasswordToken;

}
