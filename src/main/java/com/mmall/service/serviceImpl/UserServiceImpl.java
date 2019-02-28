package com.mmall.service.serviceImpl;

import com.google.zxing.client.result.EmailAddressParsedResult;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;



@Service("iUserService")
public class UserServiceImpl implements IUserService {
    
    @Autowired
    UserMapper userMapper;
    
    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("该用户不存在");
        }
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.checkLogin(username,md5Password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        return ServerResponse.createBySuccess(user);
    }
    
    
    @Override
    public ServerResponse<List<User>> getAllUser(){
        List<User> userList = userMapper.getAllUser();
        return ServerResponse.createBySuccess(userList);
    }
    
    @Override
    public ServerResponse<String> checkUsername(String username){
        int resultCount = userMapper.checkUsername(username);
        if(resultCount != 0){
            return ServerResponse.createBySuccess("该用户名已经存在");
        }
        return ServerResponse.createBySuccess();
    }
    
    @Override
    public ServerResponse<String> checkEmail(String email){
        int resultCount = userMapper.checkEmail(email);
        if(resultCount != 0){
            return ServerResponse.createBySuccess("该注册邮箱已经存在");
        }
        return ServerResponse.createBySuccess();
    }
    
    @Override
    public ServerResponse<String> forgetGetQuestion(String username){
        //校验用户的合法性
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("该用户不存在");
        }
        
        String question = userMapper.forgetGetQuestion(username);
        if(StringUtils.isBlank(question)){
            return ServerResponse.createByErrorMessage("找回的密码是空的");
        }
        
        return ServerResponse.createBySuccess(question);
    }
    
    @Override
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer){
        int resultCount = userMapper.forgetCheckAnswer(username,question,answer);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("错误问题答案");
        }
        String forgetToken = UUID.randomUUID().toString();
        TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
        return ServerResponse.createBySuccess(forgetToken);
    }
    
    @Override
    public ServerResponse<String> forgetResetPassword(String username, String newPassword, String forgetToken){
        if(StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("请传入token参数");
        }
        int resultCount= userMapper.checkUsername(username);
        if(resultCount == 0){
            ServerResponse.createByErrorMessage("用户名非法");
        }
        if(StringUtils.isBlank(TokenCache.getKey(TokenCache.TOKEN_PREFIX+username))){
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }
        if(!StringUtils.equals(forgetToken,TokenCache.getKey(TokenCache.TOKEN_PREFIX+username))){
            return ServerResponse.createByErrorMessage("token错误，请重新获取");
        }
        String MD5Password = MD5Util.MD5EncodeUtf8(newPassword);
        resultCount = userMapper.updatePassWordByUsername(username,MD5Password);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("更新密码失败");
        }
        return ServerResponse.createBySuccess("更新密码成功");
        
    }
    
    @Override
    public ServerResponse<String> resetPassword(User user, String oldPassword, String newPassword){
        int resultCount = userMapper.checkPassword(user.getUsername(),MD5Util.MD5EncodeUtf8(oldPassword));
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(newPassword));
        resultCount = userMapper.updateByPrimaryKeySelective(user);
        if(resultCount == 0){
            ServerResponse.createByErrorMessage("更新密码失败");
        }
        return ServerResponse.createBySuccess("更新密码成功");
    }
    
    
    @Override
    public ServerResponse<User> updateInformation(User user){
        int resultCount = userMapper.checkEmailByUserID(user.getEmail(),user.getId());
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("该email已经存在");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        
        resultCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("更新个人信息失败");
        }
        return ServerResponse.createBySuccess(updateUser);
    }
    
    
    @Override
    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }
    
    
    
    
    
    
    
    
}
