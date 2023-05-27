package com.foody.exception;

import lombok.Getter;

@Getter
public class RecipeAndCategoryManagerException extends RuntimeException{

    private final ErrorType errorType;

    public RecipeAndCategoryManagerException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }

    public RecipeAndCategoryManagerException(ErrorType errorType){
        this.errorType = errorType;
    }
}
