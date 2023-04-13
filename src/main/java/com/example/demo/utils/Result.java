package com.example.demo.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable{
    private Integer code;
    private String msg;
    private T data;

    public void success(T data){
        this.code = 200;
        this.msg = "成功";
        this.data = data;
    }

    public void failed(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
