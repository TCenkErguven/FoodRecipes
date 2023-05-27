package com.foody.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR(5100, "Sunucu Hatası", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4000, "Parametre Hatası", HttpStatus.BAD_REQUEST),
    PASSWORD_ERROR(4200, "Şifreler aynı değil", HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4300, "Bu kullanıcı zaten kayıtlı", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_CREATED(5400, "Kullanıcı oluşturulamadı", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4300,"Token hatası" ,  HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4400, "Böyle bir kullanıcı bulunamadı", HttpStatus.NOT_FOUND),
    RECIPE_NOT_FOUND(4500,"Recipe veritabanında mevcut değil.",HttpStatus.BAD_REQUEST),
    RECIPE_ALREADY_EXIST(5200,"Recipe veritabanında zaten mevcut",HttpStatus.BAD_REQUEST),
    NO_RECIPE_ON_FAVORITE_LIST(5300,"Favori listesinde bu recipe kayıtlı değil.",HttpStatus.BAD_REQUEST);


    private int code;
    private String message;
    HttpStatus httpStatus;
}
