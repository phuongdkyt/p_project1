package com.example.demo.services;

import com.example.demo.models.dto.MessageDTO;
import com.example.demo.models.dto.Page;
import com.example.demo.models.dto.UserDTO;
import com.example.demo.models.in.UserIn;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    ResponseEntity<MessageDTO<Object>> create(UserIn userIn);
    ResponseEntity<MessageDTO<Object>> getUser(Long id);
    ResponseEntity<MessageDTO<Object>> getMe();
}
