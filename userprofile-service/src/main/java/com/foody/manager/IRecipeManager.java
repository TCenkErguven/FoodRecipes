package com.foody.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:9070/api/v1/recipe", name = "comment-recipe" ,decode404 = true)
public interface IRecipeManager {

    @GetMapping("/does-recipe-exist/{recipeId}")
    public ResponseEntity<Boolean> doesRecipeExist(@PathVariable String recipeId);


}
