package com.foody.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class RegisterMailModel implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String activationCode;
}
