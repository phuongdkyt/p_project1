package com.example.demo.repositories;


import com.example.demo.models.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PermissionRepo extends JpaRepository<Permission,Long> {
List<Permission> findAllByIdIn(Set<Long> ids);
Optional<Permission> findByName(String name);


}
