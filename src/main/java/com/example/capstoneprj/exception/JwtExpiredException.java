package com.example.capstoneprj.exception;

public class JwtExpiredException extends RuntimeException{
    public JwtExpiredException(String mess){
        super(mess);
    }
}
