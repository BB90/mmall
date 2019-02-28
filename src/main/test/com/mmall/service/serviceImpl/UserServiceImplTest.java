package com.mmall.service.serviceImpl;

import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.junit.Test;
import org.omg.PortableInterceptor.AdapterManagerIdHelper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;

import static org.junit.Assert.*;

/**
 * @program: mmall
 * @Date: 2019/2/27 21:57
 * @Author: Mr.Wang
 * @Description:
 */
public class UserServiceImplTest {
    
    @Autowired
    IUserService iUserService;
    
    @Autowired
    UserMapper userMapper;
    
    @Test
    public void loginTest(){
        System.out.println(iUserService.getAllUser());
        System.out.println(iUserService.login("admin", "admin"));
    }
}