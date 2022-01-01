package com.example.demo.controllers;

import com.example.demo.models.dto.MessageDTO;
import com.example.demo.models.dto.Response;
import com.example.demo.models.entity.User;
import com.example.demo.models.in.UserIn;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Validated
public class UserController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/create")
    ResponseEntity<MessageDTO<Object>> login(@Valid @RequestBody UserIn userIn) {
        return adminService.create(userIn);
    }

    @GetMapping("/{id}")
    ResponseEntity<MessageDTO<Object>> read(@PathVariable("id") Long id) {
        return adminService.getUser(id);
    }

    @GetMapping("/me")
    ResponseEntity<MessageDTO<Object>> read() {
        return adminService.getMe();
    }

//    @GetMapping("/all")
//    ResponseEntity<Page<List<UserDTO>>> findAll() {
//        return adminService.getAllUser();
//    }

    @GetMapping("/get_all_user")
    ResponseEntity<MessageDTO<org.springframework.data.domain.Page<User>>> findAll(@RequestParam(value = "size",defaultValue =
            "1") int page) {
        return Response.ok(userRepo.findAll(PageRequest.of(page, 10)));
    }

    @GetMapping("/all")
    ResponseEntity<MessageDTO<List<User>>>  findAllss() {
        return userRepo.findAllUser();
    }
    @GetMapping("/find")
    ResponseEntity<MessageDTO<User>> findByUserName(@RequestParam("username") String username) {
        return Response.ok(userRepo.findByUsername(username).get());
    }
}
