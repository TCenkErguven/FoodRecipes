package com.foody;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RecipeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecipeServiceApplication.class);
    }
}
