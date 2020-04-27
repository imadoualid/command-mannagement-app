package com.projet.gateway.config.repository;

import com.projet.gateway.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    public boolean existsByUserName(String userName);
    public Optional<User> findByUserName(String userName);

}
