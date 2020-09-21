package com.chenghao.mq;

import com.chenghao.vo.Constant;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chenghao
 * @Date: 2019/8/11 23:52
 **/
@Configuration
public class RabbitmqConfig {


    //声明队列
    @Bean(Constant.QUEUE_INFORM_ALIPAY)
    public Queue QUEUE_INFORM() {
        return new Queue(Constant.QUEUE_INFORM_ALIPAY);
    }

    /**
     * 交换机配置
     * ExchangeBuilder提供了fanout、direct、topic、header交换机类型的配置
     *
     * @return the exchange
     */
    @Bean(Constant.EXCHANGE_TOPIC_INFORM_ALIPAY)
    public Exchange EXCHANGE_TOPIC_INFORM() {
        //durable(true)持久化，消息队列重启后交换机仍然存在
        return ExchangeBuilder.topicExchange(Constant.EXCHANGE_TOPIC_INFORM_ALIPAY).durable(false).build();
    }

    //绑定队列到交换机
    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(Constant.QUEUE_INFORM_ALIPAY) Queue queue,
                                              @Qualifier(Constant.EXCHANGE_TOPIC_INFORM_ALIPAY) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constant.ROUTINGKEY_INFORM_ALIPAY).noargs();
    }


}
