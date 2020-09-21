package com.chenghao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @auther Cheng Hao
 * @date 2020/9/15 21:07
 */
public interface AccountInfoMapper {

    @Update("update account_info set account_balance = account_balance + #{accountBalance} where account_no = #{accountNo}")
    int updateAccountBalance(@Param("accountNo") String accountNo, @Param("accountBalance") double accountBalance);

}
