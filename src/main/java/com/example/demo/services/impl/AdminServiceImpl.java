package com.example.demo.services.impl;

import com.example.demo.models.dto.*;
import com.example.demo.models.entity.Permission;
import com.example.demo.models.entity.User;
import com.example.demo.models.entity.UserPermission;
import com.example.demo.models.in.UserIn;
import com.example.demo.repositories.PermissionRepo;
import com.example.demo.repositories.UserPerRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.AdminService;
import com.example.demo.services.UserDetailsImpl;
import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserPerRepo userPerRepo;
    @Autowired
    private PermissionRepo permissionRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<MessageDTO<Object>> create(UserIn userIn) {
        String password = passwordEncoder.encode(userIn.getPassword());
        Optional<Permission> permissions = permissionRepo.findByName(userIn.getRole());
        if (permissions.isEmpty()) {
            return Response.badRequest("Ten quyen khong hop le! ");
        }
        User user = userRepo.save(User.builder()
                .username(userIn.getUsername())
                .password(password)
                .status(UserStatus.ACTIVE.name())
                .provider(AuthProvider.local)
                .build());
         userPerRepo.save(UserPermission.builder().perId(permissions.get().getId()).userId(user.getId()).build());

        return Response.ok();
    }

    @Override
    public ResponseEntity<MessageDTO<Object>> getUser(Long id) {

        Optional<User> user = userRepo.findByIdAndStatus(id, UserStatus.ACTIVE.name());
        if (user.isEmpty()) {
            return Response.ok("Khong thay user nao");

        }
        return Response.ok(User
                .builder()
                .username(user.get().getUsername())
                .lastLoginDate(user.get().getLastLoginDate()).build());
    }

    @Override
    public ResponseEntity<MessageDTO<Object>> getMe() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByIdAndStatus(userDetails.getId(), UserStatus.ACTIVE.name()).get();
        return Response.ok(UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername() == null ? user.getEmail() : user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .image(user.getImage())
                .email(user.getEmail())
                .build());
    }




}
