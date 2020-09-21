package com.chenghao.domain;

import lombok.Data;

/**
 * @Author: chenghao
 * @Date: 2019/8/9 18:26
 **/
@Data
public class Message {

    private Long id;
    private String userId;
    private Integer amount;
}
