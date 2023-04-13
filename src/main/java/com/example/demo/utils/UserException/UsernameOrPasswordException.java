package com.example.demo.utils.UserException;

import com.example.demo.utils.ServiceException;

public class UsernameOrPasswordException extends ServiceException {
    public UsernameOrPasswordException() {super();}

    public UsernameOrPasswordException(String message){
        super(message);
    }
}
