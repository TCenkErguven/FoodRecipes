package com.foody.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR(5100, "Sunucu Hatası", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4000, "Parametre Hatası", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4400, "Böyle bir kullanıcı bulunamadı", HttpStatus.NOT_FOUND),
    RECIPE_NOT_FOUND(4500,"Recipe veritabanında mevcut değil.",HttpStatus.BAD_REQUEST),
    RECIPE_ALREADY_EXIST(5200,"Recipe veritabanında zaten mevcut",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4600,"Token hatası" ,  HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(5300,"Category veritabanında mevcut değil",HttpStatus.NOT_FOUND),
    CATEGORY_ALREADY_EXIST(5400,"Category veritabanında mevcut",HttpStatus.BAD_REQUEST);




    private int code;
    private String message;
    HttpStatus httpStatus;
}
