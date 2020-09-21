package com.chenghao.mapper;

import com.chenghao.domain.AccountInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @auther Cheng Hao
 * @date 2020/9/15 21:07
 */
public interface AccountInfoMapper {

    /**
     * 扣减余额
     *
     * @param accountNo
     * @param accountBalance
     * @return
     */
    @Update("update account_info set account_balance = account_balance - #{accountBalance} where account_no = #{accountNo}")
    int updateAccountBalance(@Param("accountNo") String accountNo, @Param("accountBalance") double accountBalance);

    /**
     * 通过卡号查询账户
     *
     * @param accountNo
     * @return
     */
    @Select("select account_name,account_no,account_balance from account_info where account_no = #{accountNo}")
    AccountInfo getAccountInfo(@Param("accountNo") String accountNo);
}
