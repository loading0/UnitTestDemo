package com.example.demo.mapper;


import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
@Repository
@Mapper
public interface UserMapper {
    /**
     * 插入一条用户数据
     * @param user
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户名查询用户数据
     * @param username
     * @return 如果找到对应的用户则返回这个用户的数据，没有找到返回null值
     */
    User findByUsername(String username);

    /**
     * 根据id删除一条用户
     * @param id
     * @return 返回受影响行数
     */
    Integer deleteById(Integer id);

    /**
     * 更新一条数据
     * @param user
     * @return 返回受影响的行数
     */
    Integer update(User user);

    User login(String userAccount);

}

