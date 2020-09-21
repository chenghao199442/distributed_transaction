package com.chenghao.service;

/**
 * @auther Cheng Hao
 * @date 2020/9/2 22:48
 */
public interface AlipayService {

    //修改支付宝金额
    void updateAmount(String userId, int amount);

    //修改message状态
    void updateMessage(String param);
}
