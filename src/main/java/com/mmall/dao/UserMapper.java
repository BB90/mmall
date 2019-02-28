package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    int checkUsername(String username);
    
    int checkEmail(String email);
    
    User checkLogin(@Param("username") String username, @Param("password") String password);
    
    List<User> getAllUser();
    
    String forgetGetQuestion(String username);
    
    int forgetCheckAnswer(@Param("username")String username,@Param("question")String question,@Param("answer")String answer);
    
    int updatePassWordByUsername(@Param("username")String username,@Param("password")String password);
    
    int checkPassword(@Param("username")String username,@Param("password")String password);
    
    int checkEmailByUserID(@Param("email")String email,@Param("userId")Integer userId);
}