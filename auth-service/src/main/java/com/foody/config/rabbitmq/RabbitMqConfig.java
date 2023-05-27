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
    @Value("${rabbitmq.exchange-auth}")
    private String exchange;

    @Bean
    DirectExchange exchangeAuth(){
        return new DirectExchange(exchange);
    }

    //Register Mail Activation Code

    @Value("${rabbitmq.registerKey}")
    private String registerBindingKey;
    @Value("${rabbitmq.registerQueue}")
    private String registerQueue;

    @Bean
    Queue registerQueue(){
        return new Queue(registerQueue);
    }

    @Bean
    public Binding bindingRegisterMail(final Queue registerQueue,final DirectExchange exchangeAuth) {
        return BindingBuilder.bind(registerQueue).to(exchangeAuth).with(registerBindingKey);
    }

    //ForgotPassword Mail

    @Value("${rabbitmq.forgotPasswordKey}")
    private String forgotPasswordBindingKey;
    @Value("${rabbitmq.forgotPasswordQueue}")
    private String forgotPasswordQueue;

    @Bean
    Queue forgotPasswordQueue(){return new Queue(forgotPasswordQueue);}

    @Bean
    public Binding bindingForgotPasswordMail(final Queue forgotPasswordQueue,final DirectExchange exchangeAuth){
        return BindingBuilder.bind(forgotPasswordQueue).to(exchangeAuth).with(forgotPasswordBindingKey);
    }




}
