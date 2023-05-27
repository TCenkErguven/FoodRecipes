package com.foody.rabbitmq.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foody.rabbitmq.model.FavoriteRecipeAddedNotificationModel;
import com.foody.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteRecipeAddedNotificationConsumer {
    private final MailService mailService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = ("${rabbitmq.notification-mail-queue}"))
    public void sendNotificationFavoriteCategoryRecipeAdded(String jsonModel){
        try {
            FavoriteRecipeAddedNotificationModel model = objectMapper.readValue(jsonModel, FavoriteRecipeAddedNotificationModel.class);
            log.info("Mail {}", model.toString());
            mailService.sendMailFavoriteFoodAdded(model);
        }catch (JsonProcessingException e){
            e.getMessage();
        }
    }
}
