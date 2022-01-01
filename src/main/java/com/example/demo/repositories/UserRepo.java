package com.example.demo.repositories;

import com.example.demo.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepo extends JpaRepository<User,Long>, UserCustomRepo {
    Optional<User> findByUsername(String username);
    User findByUsernameAndStatus(String username,String status);
    Optional<User> findByIdAndStatus(Long id,String status);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    List<User> findAll();
}
