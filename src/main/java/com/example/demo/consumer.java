package com.example.demo;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.listener.exception.FatalListenerStartupException;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class consumer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-test",arguments = {
                    @Argument(name = "x-dead-letter-exchange", value = "exchange.dead"),
                    @Argument(name = "x-dead-letter-routing-key", value = "key.dead")}),
            exchange = @Exchange(value = "exchange-test", type = ExchangeTypes.TOPIC),
            key = "rountingKey-test")
    )

    public void receive(String message, @Headers Map<String, Object> properties, Channel channel) throws Exception {

        logger.info("consumer" + message);
 //      Long deliveryTag = (Long) properties.get(AmqpHeaders.DELIVERY_TAG);
//        try {
           int i = 1/0; //异常
//         channel.basicNack(deliveryTag, false, false); //确认
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//            //channel.basicReject(deliveryTag,true);
//        }
        //throw new RuntimeException("test");
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    exchange = @Exchange(value = "exchange.dead"),
                    value = @Queue(value = "queue.dead"),
                    key = "key.dead"
            )
    )
    public void deadListener(@Headers Map<String, Object> headers, String msg) {
        logger.info("dead consumer receive message:{headers = [" + headers + "], msg = [" + msg + "]}");
    }

//    @RabbitHandler
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "queue-test", durable = "true",arguments = {
//                    @Argument(name = "x-dead-letter-exchange", value = "dead-exchange"),
//                    @Argument(name = "x-dead-letter-routing-key", value = "dead-ley")}),
//            exchange = @Exchange(value = "exchange-test",
//                    durable = Exchange.TRUE, type = ExchangeTypes.TOPIC,
//                    ignoreDeclarationExceptions = "true"),
//            key = "rountingKey-test")
//    )
//    public void dead(String message, @Headers Map<String, Object> properties, Channel channel) throws Exception {
//
//        logger.info("error consumer receive message:{headers = [" + properties + "], msg = [" + message + "]}");
//        throw new RuntimeException("receive error!");
//    }


}
