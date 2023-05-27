package com.foody.manager;

import com.foody.dto.request.FavoriteCategoryAlerterRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:9080/api/v1/user", name = "recipe-userprofile",decode404 = true)
public interface IUserManager {

    @PutMapping("/remove-favorite-user-recipe/{recipeId}")
    public ResponseEntity<Boolean> removeFavoriteUserRecipe(@PathVariable String recipeId);

    @PostMapping("/check-user-favorite-foods-then-mail")
    public ResponseEntity<Boolean> checkUserFavoriteFoodsThenMail(@RequestBody FavoriteCategoryAlerterRequestDto dto);
}
