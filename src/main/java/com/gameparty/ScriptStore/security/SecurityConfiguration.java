package com.gameparty.ScriptStore.security;

import com.gameparty.ScriptStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;

    @Bean
    public static BCryptPasswordEncoder bPasswordEncoder() {
        return new BCryptPasswordEncoder(11);
    }


    @Bean
    public SecurityFilterChain SecurityFilterChain (HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((auth) -> {
                        try {
                            auth
                                    //Доступ только для пользователей с ролью Администратор
                                    .antMatchers("/admin/**").hasRole("ADMIN")
                                    .antMatchers("/news", "/create").hasAnyRole("USER", "ADMIN")
                                    //Доступ разрешен всем пользователей
                                    .antMatchers("/", "/home/**", "/user", "/static/**", "/registration", "/forgot_password").permitAll()
                                    //Все остальные страницы требуют аутентификации
                                    .anyRequest().authenticated()
                                    .and()
                                    //Настройка для входа в систему
                                    .formLogin().loginPage("/login")
                                    //Перенарпавление на главную страницу после успешного входа
                                    .defaultSuccessUrl("/", true)
                                    .permitAll()
                                    .and().logout().permitAll()
                                    .and().csrf().disable();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bPasswordEncoder());
    }




}
