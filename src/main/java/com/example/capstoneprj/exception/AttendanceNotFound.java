package com.example.capstoneprj.exception;

public class AttendanceNotFound extends RuntimeException{
    public AttendanceNotFound (String mess){
        super(mess);
    }
}
