package com.chenghao.mapper;

import com.chenghao.domain.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: chenghao
 * @Date: 2019/8/12 20:43
 **/
public interface MessageMapper {

    @Select("select count(1) from message where id = #{id}")
    int queryMessageCountByMessageId(Long id);

    @Insert("insert into message(id,user_id,amount) values (#{id},#{userId},#{amount})")
    Long addMessage(Message message);
}
