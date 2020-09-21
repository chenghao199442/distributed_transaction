package com.chenghao.service;

import com.chenghao.domain.Message;

/**
 * @Author: chenghao
 * @Date: 2019/8/12 20:42
 **/
public interface YuebaoService {

    void updateAmount(String userId, int amount);

    int queryMessageCountByMessageId(Long id);

    Long addMessage(Message message);
}
