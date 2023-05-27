package com.foody.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${rabbitmq.exchange-user}")
    private String exchange;

    @Bean
    DirectExchange exchangeUser(){return new DirectExchange(exchange);}

    //Mail Users About New Recipe Notificaion

    @Value("${rabbitmq.notification-mail-key}")
    private String notificationBindingKey;

    @Value("${rabbitmq.notification-mail-queue}")
    private String notificationQueue;

    @Bean
    Queue notificationQueue(){return new Queue(notificationQueue);}

    @Bean
    public Binding bindingNotificationMail(final Queue notificationQueue,final DirectExchange exchangeUser){
        return BindingBuilder.bind(notificationQueue).to(exchangeUser).with(notificationBindingKey);
    }






}
