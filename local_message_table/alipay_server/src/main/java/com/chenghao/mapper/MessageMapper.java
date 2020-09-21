package com.chenghao.mapper;

import com.chenghao.domain.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: chenghao
 * @Date: 2019/8/9 20:40
 **/
public interface MessageMapper {

    @Update("update message set status = #{status} where id = #{id}")
    int update(Message message);

    @Insert("insert into message(user_id,amount,status) values (#{userId},#{amount},'unConfirm')")
    Long addMessage(Message message);

    @Select("select * from message where status = #{status}")
    List<Message> queryByStatus(String status);
}
