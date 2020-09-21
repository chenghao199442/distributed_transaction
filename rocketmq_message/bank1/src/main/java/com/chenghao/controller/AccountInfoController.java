package com.chenghao.controller;

import com.chenghao.domain.AccountChangeEvent;
import com.chenghao.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @auther Cheng Hao
 * @date 2020/9/15 21:43
 */
@RestController
@Slf4j
public class AccountInfoController {

    @Autowired
    private AccountInfoService accountInfoService;

    /**
     * 模拟转账
     * @param producerAccountNo
     * @param accountBalance
     * @param consumerAccountNo
     * @return
     */
    @GetMapping("/transfer")
    public String transfer(String producerAccountNo, double accountBalance, String consumerAccountNo) {
        //把转账卡号与金额组装为一个对象
        AccountChangeEvent accountChangeEvent = new AccountChangeEvent(producerAccountNo, consumerAccountNo,
                accountBalance, UUID.randomUUID().toString());
        //发送消息
        accountInfoService.sendUpdateAccountBalance(accountChangeEvent);
        return "转账成功";
    }

}
