package com.example.demo.services;

import com.example.demo.models.dto.MessageDTO;
import com.example.demo.models.in.LoginDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;


public interface AuthService {
    ResponseEntity<MessageDTO<Object>> login(HttpServletRequest request, LoginDTO loginDTO);
     String testSave();
}
