package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer userId;
    private String userAccount;
    private String password;
    private String salt;
    private String userName;
    private String email;

}
