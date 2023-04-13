package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.utils.UserException.UsernameDuplicatedException;
import com.example.demo.utils.UserException.UsernameOrPasswordException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    UserService userService;

    @BeforeAll
    public static void setUp() {

    }

    @AfterAll
    public static void tearDown() {
    }

    @Test
//    @ParameterizedTest
//    @MethodSource("registerData")
    void registerException() {
        User u1 = new User();
        u1.setUserAccount("admin");
        u1.setPassword("123456");
        Assertions.assertThrows(UsernameDuplicatedException.class, ()->{
            userService.register(u1);
        });
    }

    @Test
    @ParameterizedTest
    @MethodSource("registerData")
    void register(){

    }

    @Test
    void login() {
        User u1 = new User();
        u1.setUserAccount("admint");
        u1.setPassword("123456");
        Assertions.assertThrows(UsernameOrPasswordException.class, ()->{
            userService.login(u1);
        });
    }
}