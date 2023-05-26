package com.gameparty.ScriptStore.service;

import com.gameparty.ScriptStore.entity.Role;
import com.gameparty.ScriptStore.entity.User;
import com.gameparty.ScriptStore.exception.PasswordsNotMatchException;
import com.gameparty.ScriptStore.exception.UserAlreadyExistException;
import com.gameparty.ScriptStore.exception.UserNotFoundException;
import com.gameparty.ScriptStore.model.UserModel;
import com.gameparty.ScriptStore.repository.RoleRepository;
import com.gameparty.ScriptStore.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceI {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public boolean registration(User user) throws UserAlreadyExistException, PasswordsNotMatchException {
        if (IsUserExistByUsername(user.getUsername())) {
            if (IsUserExistByEmail(user.getEmail())) {
                throw new UserAlreadyExistException("Пользователь с такими никнеймом и почтой уже существует");
            }
            throw new UserAlreadyExistException("Пользователь с таким никнеймом уже существует");
        } else if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("Пользователь с такой почтой уже существует");
        } else if (!user.getPassword().equals(user.getMatchingPassword())) {
            throw new PasswordsNotMatchException("Пароли не совпадают");
        }
        User userEntity = new User();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        if(role == null){
            role = checkRoleExist();
        }
        userEntity.setRoles(List.of(role));
        userRepository.save(userEntity);
        return true;
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    @Override
    public UserModel getUserById(Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);

        UserModel userModel = UserModel.toModel(user.orElseThrow(() -> new UserNotFoundException("Пользователь не найден")));

        return userModel;
    }

    @Override
    public UserModel getUserByUsername(String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        UserModel userModel = UserModel.toModel(user.orElseThrow(() -> new UserNotFoundException("Пользователь с именем" + username + "не найден")));

        return userModel;
    }

    @Override
    public UserModel getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        UserModel userModel = UserModel.toModel(user.orElseThrow(() -> new UserNotFoundException("Пользователь с почтой" + email + "не найден")));

        return userModel;
    }

    //    привести к модели и исключение
    @Override
    public List<UserModel> getAllUsers() throws UserNotFoundException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("Пользователей не найдено");
        }
        List<UserModel> userModels = users.stream().map(x -> UserModel.toModel(x)).collect(Collectors.toList());
        return userModels;
    }

    @Override
    public boolean deleteUser(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        userRepository.delete(user);
        return true;
    }

    //    выводит пользователь начиная с idMin
    @Override
    public List<UserModel> getUserList(int page) {
        int start = (page - 1) * 10;
        int end = 10 * page - 1;
        Pageable pageable = PageRequest.of(start,end);
        List<User> users = userRepository.findAll(pageable).toList();
        List<UserModel> userModels = users.stream().map(x -> UserModel.toModel(x)).collect(Collectors.toList());
        return userModels;
    }

    @Override
    public Boolean IsUserExistByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public Boolean IsUserExistByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        User user =  userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Пользователь с почтой" + email + "не найден"));
        return user;
    }



    public boolean updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Пользователь с почтой" + email + "не найден"));
            user.setResetPasswordToken(token);
            userRepository.save(user);
            return true;
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token).get();
    }

    public boolean updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);

        return true;
    }
}
