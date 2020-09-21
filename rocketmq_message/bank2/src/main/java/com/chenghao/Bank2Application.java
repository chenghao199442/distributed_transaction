package com.chenghao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @auther Cheng Hao
 * @date 2020/9/15 21:46
 */
@MapperScan("com.chenghao.mapper")
@SpringBootApplication
public class Bank2Application {

    public static void main(String[] args) {
        SpringApplication.run(Bank2Application.class, args);
    }

}
