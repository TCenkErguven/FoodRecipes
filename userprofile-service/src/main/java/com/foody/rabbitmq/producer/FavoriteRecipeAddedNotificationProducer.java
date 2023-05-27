package com.foody.rabbitmq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foody.rabbitmq.model.FavoriteRecipeAddedNotificationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteRecipeAddedNotificationProducer {
    @Value("${rabbitmq.exchange-user}")
    private String exchange;
    @Value("${rabbitmq.notification-mail-key}")
    private String notificationBindingKey;


    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendNotificationFavoriteRecipeAdded(FavoriteRecipeAddedNotificationModel model){
        try {
            String senderModel = objectMapper.writeValueAsString(model);
            rabbitTemplate.convertAndSend(exchange, notificationBindingKey, senderModel);
        }catch (JsonProcessingException e){
            e.getMessage();
        }
    }

}
