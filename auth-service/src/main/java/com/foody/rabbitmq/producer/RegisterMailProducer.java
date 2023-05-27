package com.foody.rabbitmq.producer;

import com.foody.rabbitmq.model.RegisterMailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterMailProducer {

    @Value("${rabbitmq.exchange-auth}")
    private String exchange;
    @Value("${rabbitmq.registerKey}")
    private String registerMailKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendActivationCode(RegisterMailModel model){
        rabbitTemplate.convertAndSend(exchange,registerMailKey,model);
    }


}
