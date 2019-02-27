package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: mmall
 * @Date: 2019/2/21 14:35
 * @Author: Mr.Wang
 * @Description:
 */



public interface IUserService {
    ServerResponse<User> login(String username, String password);
    
    ServerResponse<List<User>> getAllUser();
    
    ServerResponse<String> checkUsername(String username);
    
    ServerResponse<String> checkEmail(String email);
    
    ServerResponse<String> forgetGetQuestion(String username);
    
    ServerResponse<String> forgetCheckAnswer(String username,String question,String answer);
    
    ServerResponse<String> forgetResetPassword(String username,String newPassword,String forgetToken);
    
    ServerResponse<String> resetPassword(User user, String oldPassword, String newPassword);
    
    ServerResponse<User> updateInformation(User user);
    
    ServerResponse<User> getInformation(Integer userId);
    
}
