package com.foody.utility;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenProvider {
    @Value("${secretkey}")
    String secretKey;
    @Value("${audience}")
    String audience;
    @Value("${issuer}")
    String issuer;


    public Optional<String> createToken(Long id, String activationCode){
        String token = null;
        Date date = new Date(System.currentTimeMillis() + (1000*60*1));
        try {
            System.out.println(id);
            System.out.println(activationCode);
            token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(date)
                    .withClaim("id", id)
                    .withClaim("activationCode", activationCode)
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}