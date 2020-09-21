package com.chenghao.service;

import com.chenghao.domain.AccountChangeEvent;

/**
 * @auther Cheng Hao
 * @date 2020/9/15 21:16
 */
public interface AccountInfoService {

    /**
     * 发送扣减余额的消息
     * @param accountChangeEvent
     */
    void sendUpdateAccountBalance(AccountChangeEvent accountChangeEvent);

    /**
     * 扣减余额
     * @param accountChangeEvent
     */
    void updateAccountInfoBalance(AccountChangeEvent accountChangeEvent);
}
