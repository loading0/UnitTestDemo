package com.example.demo.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult<T> implements Serializable {
    private Integer state;
    private String message;
    private T data;
}

