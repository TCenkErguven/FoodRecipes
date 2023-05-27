package com.foody.exception;

import lombok.Getter;

@Getter
public class CommentAndPointManagerException extends RuntimeException{

    private final ErrorType errorType;

    public CommentAndPointManagerException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }

    public CommentAndPointManagerException(ErrorType errorType){
        this.errorType = errorType;
    }
}
