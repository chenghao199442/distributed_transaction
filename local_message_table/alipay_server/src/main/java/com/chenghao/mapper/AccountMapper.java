package com.chenghao.mapper;

import com.chenghao.domain.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: chenghao
 * @Date: 2019/8/9 20:40
 **/
public interface AccountMapper {

    @Update("update account set amount = amount - #{amount},update_time = now() where user_id = #{userId}")
    int updateAccount(Account account);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert account(user_id,amount,update_time) values (#{userId},#{amount},now())")
    Long addAccount(Account account);

    @Select("select * from account")
    List<Account> queryAll();

}
