package com.chenghao.service.impl;

import com.chenghao.domain.Account;
import com.chenghao.domain.Message;
import com.chenghao.mapper.AccountMapper;
import com.chenghao.mapper.MessageMapper;
import com.chenghao.service.YuebaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: chenghao
 * @Date: 2019/8/12 20:42
 **/
@Service
public class YuebaoServiceImpl implements YuebaoService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void updateAmount(String userId, int amount) {
        Account account = new Account();
        account.setUserId(userId);
        account.setAmount(amount);
        accountMapper.updateAmount(account);
    }

    @Override
    public int queryMessageCountByMessageId(Long id) {
        return messageMapper.queryMessageCountByMessageId(id);
    }

    @Override
    public Long addMessage(Message message) {
        return messageMapper.addMessage(message);
    }
}
