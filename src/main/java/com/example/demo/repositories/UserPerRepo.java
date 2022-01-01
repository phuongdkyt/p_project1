package com.example.demo.repositories;

import com.example.demo.models.entity.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPerRepo extends JpaRepository<UserPermission,Long> {
    List<UserPermission> findAllByUserId(Long userId);
}
