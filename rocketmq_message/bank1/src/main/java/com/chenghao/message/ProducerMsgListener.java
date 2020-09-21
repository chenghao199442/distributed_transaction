package com.chenghao.message;

import com.alibaba.fastjson.JSONObject;
import com.chenghao.domain.AccountChangeEvent;
import com.chenghao.mapper.MessageLogMapper;
import com.chenghao.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @auther Cheng Hao
 * @date 2020/9/15 21:32
 */
@Slf4j
@Component
@RocketMQTransactionListener(txProducerGroup = "producer_group_bank1")
public class ProducerMsgListener implements RocketMQLocalTransactionListener {

    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private MessageLogMapper messageLogMapper;

    /**
     * 监听mq的半消息回调，然后执行本地事务
     *
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            //获取消息
            AccountChangeEvent accountChangeEvent = getAccountChangeEventByMessage(msg);
            //执行本地事务
            accountInfoService.updateAccountInfoBalance(accountChangeEvent);
            //没抛异常，就commit
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("本地事务处理失败");
            //异常rollback
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 回查事务执行状态
     *
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        //获取消息
        AccountChangeEvent accountChangeEvent = getAccountChangeEventByMessage(msg);
        //判断事务执行情况
        int existTx = messageLogMapper.isExistTx(accountChangeEvent.getTxNo());
        if (existTx > 0) {
            //有记录就commit
            return RocketMQLocalTransactionState.COMMIT;
        }
        //如果状态返回unknown，mq会定时再来回查，默认回查15次，超过后回滚
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    /**
     * 获取消息信息
     *
     * @param msg
     * @return
     */
    private AccountChangeEvent getAccountChangeEventByMessage(Message msg) {
        String messageStr = new String((byte[]) msg.getPayload());
        JSONObject jsonObject = JSONObject.parseObject(messageStr);
        String accountChange = jsonObject.getString("accountChange");
        return JSONObject.parseObject(accountChange, AccountChangeEvent.class);
    }

}
