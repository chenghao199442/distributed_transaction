package com.chenghao.service.impl;

import com.chenghao.domain.AccountChangeEvent;
import com.chenghao.mapper.AccountInfoMapper;
import com.chenghao.mapper.MessageLogMapper;
import com.chenghao.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @auther Cheng Hao
 * @date 2020/9/15 21:57
 */
@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    private AccountInfoMapper accountInfoMapper;
    @Autowired
    private MessageLogMapper messageLogMapper;

    /**
     * 添加金额
     * @param accountChangeEvent
     */
    @Transactional
    @Override
    public void addAccountInfoBalance(AccountChangeEvent accountChangeEvent) {
        log.info("bank2更新本地账号，账号:{},金额:{}",accountChangeEvent.getConsumerAccountNo(), accountChangeEvent.getAccountBalance());
        //幂等操作
        if (messageLogMapper.isExistTx(accountChangeEvent.getTxNo()) > 0) {
            return;
        }
        //修改金额
        accountInfoMapper.updateAccountBalance(accountChangeEvent.getConsumerAccountNo(), accountChangeEvent.getAccountBalance());
        //添加转账记录
        messageLogMapper.addTx(accountChangeEvent.getTxNo());
    }
}
