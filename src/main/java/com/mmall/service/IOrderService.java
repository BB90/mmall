package com.mmall.service;

import com.mmall.common.ServerResponse;

import java.util.Map;

/**
 * @program: mmall
 * @Date: 2019/2/27 20:31
 * @Author: Mr.Wang
 * @Description:
 */
public interface IOrderService {
    
    ServerResponse<Map<String, String>> pay(Long orderNo, Integer userId);
}
