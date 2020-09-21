package com.chenghao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @auther Cheng Hao
 * @date 2020/9/2 21:35
 */
@SpringBootApplication
@MapperScan(basePackages = "com.chenghao.mapper")
@EnableScheduling
public class AlipayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlipayServerApplication.class, args);
    }

}
