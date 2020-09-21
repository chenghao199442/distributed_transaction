package com.chenghao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chenghao.domain.Account;
import com.chenghao.domain.Message;
import com.chenghao.mapper.AccountMapper;
import com.chenghao.mapper.MessageMapper;
import com.chenghao.mq.RabbitmqSender;
import com.chenghao.service.AlipayService;
import com.chenghao.vo.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @Author: chenghao
 * @Date: 2019/8/11 23:26
 **/
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private RabbitmqSender rabbitmqSender;

    @Override
    public void updateAmount(String userId, int amount) {
        //这里为什么要用编程式事务，因为消息发送不是一个事务操作，不是一个dml操作，放在事务里面不合适，
        //而且放到事务里面， 如果消息没有发送成功，数据库资源就被占用了
        Long row = (Long) transactionTemplate.execute((TransactionCallback<Object>) transactionStatus -> {
            //先是修改支付宝金额
            Account account = new Account();
            account.setAmount(amount);
            account.setUserId(userId);
            int i = accountMapper.updateAccount(account);
            //如果修改成功，则新增一条消息到表中
            if (i == 1) {
                Message message = new Message();
                message.setAmount(amount);
                message.setUserId(userId);
                messageMapper.addMessage(message);
                return message.getId();
            }
            return null;
        });
        if (row != null) {
            //往mq里面插入消息
            Message message = new Message();
            message.setAmount(amount);
            message.setUserId(userId);
            message.setStatus("unConfirm");
            message.setId(row);
            rabbitmqSender.sendMessage(Constant.EXCHANGE_TOPIC_INFORM_ALIPAY,
                    Constant.ROUTINGKEY_INFORM_ALIPAY, message);
        }
    }

    @Transactional
    @Override
    public void updateMessage(String param) {
        //{code:"ok",messageId:messageId}
        JSONObject jsonObject = JSONObject.parseObject(param);
        Long messageId = jsonObject.getLong("messageId");
        String code = jsonObject.getString("code");
        if ("ok".equals(code)) {
            Message message = new Message();
            message.setId(messageId);
            message.setStatus("confirm");
            messageMapper.update(message);
        }

    }
}
