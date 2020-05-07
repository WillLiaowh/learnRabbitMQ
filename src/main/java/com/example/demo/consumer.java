package com.example.demo;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


@Component
public class consumer {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-test",durable = "true"
                    ,arguments = {
                    @Argument(name = "x-dead-letter-exchange", value = "exchange-dead"),
                    @Argument(name = "x-dead-letter-routing-key", value = "key-dead")}
                    ),

            exchange = @Exchange(value = "exchange-test",durable = "true",ignoreDeclarationExceptions = "true"),
            key = "rountingKey-test")
    )
    @RabbitHandler
    public void receive(String message, @Headers Map<String, Object> properties, Channel channel) throws IOException {

       logger.info("consumer" + message);

      Long deliveryTag = (Long) properties.get(AmqpHeaders.DELIVERY_TAG);
        try {
            //int i = 1/0; //异常
            channel.basicAck(deliveryTag, false); //确认
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicReject(deliveryTag,false);//拒绝
        }
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    exchange = @Exchange(value = "exchange-dead"),
                    value = @Queue(value = "queue-dead"),
                    key = "key-dead"
            )
    )
    @RabbitHandler
    public void deadListener(@Headers Map<String, Object> headers, String msg) {
        logger.info("dead consumer receive message:{headers = [" + headers + "], msg = [" + msg + "]}");
    }

}
