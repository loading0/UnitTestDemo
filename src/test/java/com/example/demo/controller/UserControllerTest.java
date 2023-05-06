package com.example.demo.controller;

import com.alibaba.fastjson2.JSON;
import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;



@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @Autowired
    UserController userController;
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Rollback(value = true)
    @Transactional
    void register() {
        MvcResult mvcResult = null;
//        www-form-urlencode格式传送参数，contentType = APPLICATION_FORM_URLENCODED
//        MultiValueMap param = new LinkedMultiValueMap();
//        param.add("userAccount","abc");
//        param.add("password","123456");
//        param.add("userName", "tester");
        User user = new User();
        user.setUserAccount("loginuser1");
        user.setPassword("123456");
        user.setUserName("tester");
        String jsonStrUser = JSON.toJSONString(user);
        System.out.println(jsonStrUser);
        try{
            mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                            .accept(MediaType.APPLICATION_JSON_UTF8)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
//                            .params(param))
                            .content(jsonStrUser))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("注册成功")));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/wrongLogin.csv")
    void login(String reqJson, String repJson) {
        try{
            mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                            .accept(MediaType.APPLICATION_JSON_UTF8)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(reqJson))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(repJson)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}