package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.UserException.InsertException;
import com.example.demo.utils.UserException.UsernameDuplicatedException;
import com.example.demo.utils.Result;
import com.example.demo.utils.UserException.UsernameOrPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public Result testMeothd(){
        Result result = new Result();
        result.success("测试数据成功");
        return result;
    }


    @GetMapping("/{username}")
    public Result findByUsername(@PathVariable String username){
        Result result = new Result();
        User user = userService.findByUsername(username);
        if( null == user){
            result.failed(4001, "查询无该用户");
        }else{
            result.success(user);
        }

        return result;
    }


    @PostMapping(value = "/register")
    @ResponseBody
    public Result register(@RequestBody User user){
        Result result = new Result();
        try {
            userService.register(user);
            result.setCode(200);
            result.setMsg("注册成功");
        } catch (UsernameDuplicatedException e) {
            result.setCode(4000);
            result.setMsg("用户名被占用");
        }catch (InsertException e){
            result.setCode(5000);
            result.setMsg("注册时产生异常");
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping(value = "/login")
    public Result login(@RequestBody User user){
        Result result = new Result();
        try {
            User userInfo = userService.login(user);
            HashMap <String, String> data = new HashMap<>();
            data.put("userId", String.valueOf(userInfo.getUserId()));
            data.put("userName", userInfo.getUserName());
            data.put("userAccount", userInfo.getUserAccount());
            result.setCode(200);
            result.setMsg("登录成功");
            result.setData(data);

        } catch (UsernameOrPasswordException e) {
            result.setCode(4002);
            result.setMsg("用户名或密码错误");
        } catch (Exception e){
            result.setCode(5000);
            result.setMsg("登录发生异常");
            e.printStackTrace();
        }
        return result;
    }

}

