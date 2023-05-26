package com.gameparty.ScriptStore.service;

import com.gameparty.ScriptStore.entity.User;
import com.gameparty.ScriptStore.exception.PasswordsNotMatchException;
import com.gameparty.ScriptStore.exception.UserAlreadyExistException;
import com.gameparty.ScriptStore.exception.UserNotFoundException;
import com.gameparty.ScriptStore.model.UserModel;

import java.util.List;


public interface UserServiceI {
    boolean registration(User user) throws UserAlreadyExistException, PasswordsNotMatchException;

    UserModel getUserById(Long userId) throws UserNotFoundException;

    UserModel getUserByUsername(String username) throws UserNotFoundException;

    UserModel getUserByEmail(String email) throws UserNotFoundException;

    List<UserModel> getAllUsers() throws UserNotFoundException;

    boolean deleteUser(Long userId) throws UserNotFoundException;

    List<UserModel> getUserList(int page);

    Boolean IsUserExistByUsername(String username);

    Boolean IsUserExistByEmail(String email);

    User findUserByEmail(String email) throws UserNotFoundException;

    boolean updateResetPasswordToken(String token, String email) throws UserNotFoundException;

    boolean updatePassword(User user, String newPassword);
}
