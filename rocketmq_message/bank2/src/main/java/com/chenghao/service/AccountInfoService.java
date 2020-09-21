package com.chenghao.service;

import com.chenghao.domain.AccountChangeEvent;

/**
 * @auther Cheng Hao
 * @date 2020/9/15 21:55
 */
public interface AccountInfoService {

    /**
     * 添加金额
     * @param accountChangeEvent
     */
    void addAccountInfoBalance(AccountChangeEvent accountChangeEvent);

}
