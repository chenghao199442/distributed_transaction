package com.chenghao.domain;

import lombok.Data;

/**
 * @auther Cheng Hao
 * @date 2020/9/16 19:07
 */
@Data
public class AccountInfo {

    private Long id;
    //账户名
    private String accountName;
    //账户号
    private String accountNo;
    //账户密码
    private String accountPassword;
    //账户余额
    private double accountBalance;

}
