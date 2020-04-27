package com.projet.gateway.service;

import com.projet.gateway.model.User;

import java.util.List;

public interface UserService {

    public List<User> findUsers();
    public void deleteUser(Integer id);
    public User addUser(User user);
    public User updatedUser(User user);

    boolean checkIfIdexists(Integer id);
    boolean checkIfUsernameExist(String userName);

    User findUser(Integer userId);
}
