package com.projet.gateway.service;

import com.projet.gateway.config.repository.UserRepository;
import com.projet.gateway.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl  implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public List<User> findUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public void deleteUser(Integer id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User addUser(User u) {
        return this.userRepository.save(u);
    }



    @Override
    public User updatedUser(User u) {
        return this.userRepository.save(u);
    }

    @Override
    public boolean checkIfIdexists(Integer id) {
        return userRepository.existsById(id);
    }
    @Override
    public boolean checkIfUsernameExist(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public User findUser(Integer userId) {
        return this.userRepository.findById(userId).get();
    }
}
