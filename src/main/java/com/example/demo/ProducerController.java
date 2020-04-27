package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    public ProducerController(RabbitTemplate rabbitTemplate){
//        this.rabbitTemplate = rabbitTemplate;
//        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//            @Override
//            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
//                logger.info("returnedMessage" + message);
//            }
//        });
//    }

    @GetMapping("/publish")
    public void publish(){
        String message = "message from producer";
        logger.info("publish" + message);
//        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//            @Override
//            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
//                logger.info("returnedMessage" + message);
//            }
//        });

        rabbitTemplate.convertAndSend("exchange-test","rountingKey-test",message);
//        rabbitTemplate.convertAndSend("exchange-test","rountingKey-test",message, new MessagePostProcessor() {
//            @Override
//            public Message postProcessMessage(Message message) throws AmqpException {
//                MessageProperties properties = message.getMessageProperties();
//                properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
//                return message;
//            }
//        });

    }
}
