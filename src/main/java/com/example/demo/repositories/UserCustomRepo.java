package com.example.demo.repositories;

import com.example.demo.models.dto.MessageDTO;
import com.example.demo.models.dto.Page;
import com.example.demo.models.dto.UserDTO;
import com.example.demo.models.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserCustomRepo {
    ResponseEntity<MessageDTO<List<User>>> findAllUser();
    ResponseEntity<MessageDTO<List<User>>> find100User();
}
