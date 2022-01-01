package com.example.demo.controllers;


import com.example.demo.models.dto.MessageDTO;
import com.example.demo.models.in.LoginDTO;
import com.example.demo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    ResponseEntity<MessageDTO<Object>> login(HttpServletRequest request,@RequestBody LoginDTO loginDTO) {
        return authService.login(request,loginDTO);
    }
    @GetMapping("/test/batch")
    String test(){
       return authService.testSave();
    }


}
