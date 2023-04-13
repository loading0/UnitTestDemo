package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.ArrayList;


public interface UserService {

    void register(User user);

    User findByUsername( String userName);

    User login(User user);
}
