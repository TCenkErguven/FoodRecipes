package com.foody.rabbitmq.consumer;

import com.foody.rabbitmq.model.RegisterMailModel;
import com.foody.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterMailConsumer {
    private final MailService mailService;

    @RabbitListener(queues = ("${rabbitmq.registerQueue}"))
    public void sendActivationCode(RegisterMailModel model){
        log.info("Mail {}", model.toString());
        mailService.sendMailActivationCode(model);
    }
}
