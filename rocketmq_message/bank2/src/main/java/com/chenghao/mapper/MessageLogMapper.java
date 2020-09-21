package com.chenghao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @auther Cheng Hao
 * @date 2020/9/15 21:10
 */
public interface MessageLogMapper {

    @Select("select count(1) from message_log where tx_no = #{txNo}")
    int isExistTx(String txNo);

    @Insert("insert into message_log values(#{txNo},now())")
    int addTx(String txNo);

}
