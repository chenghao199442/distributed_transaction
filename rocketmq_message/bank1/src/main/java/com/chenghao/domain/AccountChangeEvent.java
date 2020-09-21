package com.chenghao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @auther Cheng Hao
 * @date 2020/9/15 21:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountChangeEvent implements Serializable {

    //转账人账号
    private String producerAccountNo;
    //接收人账号
    private String consumerAccountNo;
    //转账金额
    private double accountBalance;
    //转账记录id
    private String txNo;

}
