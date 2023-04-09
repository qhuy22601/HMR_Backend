package com.example.capstoneprj.domain.model;

import lombok.Data;

public enum PayGrade {
    INTERNSHIP(2000000),
    FRESHER(5000000),
    JUNIOR(1000000),
    SENIOR(1500000),
    VICEDIRECTOR(25000000),
    DIRECTOR(35000000);
    private int value;
    private PayGrade(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}