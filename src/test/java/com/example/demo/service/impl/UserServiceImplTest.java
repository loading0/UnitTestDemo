package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.utils.UserException.UsernameDuplicatedException;
import com.example.demo.utils.UserException.UsernameOrPasswordException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.stream.Stream;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    UserService userService;

    @BeforeAll
    public static void setUp() {

    }

    @AfterAll
    public static void tearDown() {
    }


    /** 用户注册发生异常的测试案例 */
    @Test
    void registerException() {
        User u1 = new User();
        u1.setUserAccount("admin");
        u1.setPassword("123456");
        Assertions.assertThrows(UsernameDuplicatedException.class, ()->{
            userService.register(u1);
        });
    }

    /** 用户登录成功的案例 */
    @ParameterizedTest
    @MethodSource("loginData")
    void login(User userLoginData){
        User userInfo = userService.login(userLoginData);
        Assertions.assertEquals(userLoginData.getUserAccount(),userInfo.getUserAccount());
    }

    /** 用户登录成功案例的数据源 */
    static Stream loginData(){
        ArrayList<User> userList = new ArrayList<>();
        User u1 = new User();
        User u2 = new User();
        u1.setUserAccount("admin");
        u1.setPassword("123456");
        u2.setUserAccount("root");
        u2.setPassword("123456");
        userList.add(u1);
        userList.add(u2);
        return Stream.of(u1, u2);
    }

    /** 用户登录发生异常的测试案例 */
    @Test
    void loginException() {
        User u1 = new User();
        u1.setUserAccount("admint");
        u1.setPassword("123456");
        Assertions.assertThrows(UsernameOrPasswordException.class, ()->{
            userService.login(u1);
        });
    }
}