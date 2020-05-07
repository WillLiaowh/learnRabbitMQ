package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
    }

    RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            if(!ack) {
                logger.error("ConfirmCallback:     " + "相关数据：" + correlationData);
                logger.error("ConfirmCallback:     " + "确认情况：" + ack);
                logger.error("ConfirmCallback:     " + "原因：" + cause);
            }
        }
    };

    RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            logger.error("ReturnCallback:     " + "消息：" + message);
            logger.error("ReturnCallback:     " + "回应码：" + replyCode);
            logger.error("ReturnCallback:     " + "回应信息：" + replyText);
            logger.error("ReturnCallback:     " + "交换机：" + exchange);
            logger.error("ReturnCallback:     " + "路由键：" + routingKey);
        }
    };

}
