package com.chenghao.controller;

import com.chenghao.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther Cheng Hao
 * @date 2020/9/2 22:47
 */
@RestController
public class AlipayController {

    @Autowired
    private AlipayService alipayService;

    /**
     * 转账
     *
     * @param userId
     * @param amount
     * @return
     */
    @RequestMapping("/transfer")
    public String transfer(String userId, int amount) {
        try {
            alipayService.updateAmount(userId, amount);
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }

    /**
     * 进行回调
     *
     * @param param
     * @return
     */
    @RequestMapping("/callBack")
    public String callBack(String param) {
        try {

        } catch (Exception e) {
            return "error";
        }
        return "success";
    }

}
