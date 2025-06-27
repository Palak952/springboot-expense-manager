package com.example.usercrud.exception;

import com.example.usercrud.model.User;

public class UserNotFound extends Exception{
    public UserNotFound(String message){
        super(message);
    }
}
