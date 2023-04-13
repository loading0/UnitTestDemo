package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.utils.UserException.UsernameOrPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.utils.UserException.UsernameDuplicatedException;
import com.example.demo.utils.UserException.InsertException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public void register(User user){
      User result = userMapper.login(user.getUserAccount());
        if (result!=null){
            throw new UsernameDuplicatedException("用户名被占用");
        }else {
            //如果结果为null,则执行注册业务
            String oldPassword=user.getPassword();
            String salt= UUID.randomUUID().toString().toUpperCase();
            user.setSalt(salt);
            String md5Password = getMd5Password(oldPassword, salt);
            System.out.println(md5Password);
            user.setPassword(md5Password);
            Integer rows = userMapper.insert(user);
            if (rows!=1){
                throw new InsertException("注册失败");
            }
        }
    }
    private String getMd5Password(String password,String salt){
        for (int i = 0; i < 3; i++) {
            //三次加密
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        //返回加密的密码
        return password;
    }
    @Override
    public User findByUsername(String userName){
        return userMapper.findByUsername(userName);
//        return userMapper.findByUsername(userName);
    }

    @Override
    public User login(User user){
        User userInfo = userMapper.login(user.getUserAccount());
        if(userInfo == null){
            throw new UsernameOrPasswordException("用户名或密码错误");
        }else{
            String salt = userInfo.getSalt();
            String encodedPassword = userInfo.getPassword();
            boolean passRight = encodedPassword.equals(getMd5Password(user.getPassword(),salt));
            if(!passRight) {
                throw new UsernameOrPasswordException("用户名或密码错误");
            }
        }
        return userInfo;
    }
}
