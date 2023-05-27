package com.foody.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    //Register Mail Activation Code

    @Value("${rabbitmq.registerQueue}")
    private String registerQueue;
    @Bean
    Queue registerQueue(){
        return new Queue(registerQueue);
    }

    //ForgotPassword Mail

    @Value("${rabbitmq.forgotPasswordQueue}")
    private String forgotPasswordQueue;
    @Bean
    Queue forgotPasswordQueue(){
        return new Queue(forgotPasswordQueue);
    }

    //Mail Users About New Recipe Notificaion

    @Value("${rabbitmq.notification-mail-queue}")
    private String notificationQueue;

    @Bean
    Queue notificationQueue(){return new Queue(notificationQueue);}





}
