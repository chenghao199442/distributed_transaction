package com.chenghao.mapper;

import com.chenghao.domain.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

/**
 * @Author: chenghao
 * @Date: 2019/8/12 20:44
 **/
public interface AccountMapper {
    @Update("update account set amount = amount + #{amount},update_time = now() where user_id = #{userId}")
    int updateAmount(Account account);

    @Insert("insert into account(user_id,amount,update_time) values(#{userId},#{amount},now())")
    Long addAccount(Account account);
}
