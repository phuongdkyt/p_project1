package com.example.demo.services.impl;

import com.example.demo.models.in.UserIn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceImplTest {


    @Test
    void create() {

        UserIn userIn = UserIn.builder().username("user").password("123456").role("USER").build();

    }
}