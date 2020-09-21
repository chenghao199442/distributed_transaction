package com.chenghao.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author: chenghao
 * @Date: 2019/8/9 18:03
 **/
@Data
public class Account {

    private Long id;
    private String userId;
    private int amount;
    private Date updateTime;

}
