package com.foody.service;

import com.foody.rabbitmq.model.FavoriteRecipeAddedNotificationModel;
import com.foody.rabbitmq.model.ForgotPasswordMailModel;
import com.foody.rabbitmq.model.RegisterMailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    public void sendMailActivationCode(RegisterMailModel model){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Activation Code");
        mailMessage.setFrom("${spring.mail.username}");
        mailMessage.setTo(model.getEmail());
        mailMessage.setText(model.getUsername() + "\nActivation Code: " + model.getActivationCode());
        javaMailSender.send(mailMessage);
    }

    public void sendMailForgotPassword(ForgotPasswordMailModel model){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Forgot Password");
        mailMessage.setFrom("${spring.mail.username}");
        mailMessage.setTo(model.getEmail());
        mailMessage.setText(model.getUsername() + "\nNew Password: " + model.getPassword());
        javaMailSender.send(mailMessage);
        System.out.println(mailMessage);
    }

    public void sendMailFavoriteFoodAdded(FavoriteRecipeAddedNotificationModel model) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("A New Recipe For Your Liking");
        mailMessage.setFrom("${spring.mail.username}");
        model.getUserProfileMailRequestDtos().forEach(userProfile -> {
            mailMessage.setTo(userProfile.getEmail());
            String message = "Dear " + userProfile.getUsername() + "\n" +model.getFoodName() +
                    " has been newly added for your demise.\nCome and check today." +
                    "Recipe Categories" ;
            for(String categoryName : model.getCategoryNames()){
                message += " " + categoryName;
            }
            mailMessage.setText(message);
            javaMailSender.send(mailMessage);
        });












    }
}
