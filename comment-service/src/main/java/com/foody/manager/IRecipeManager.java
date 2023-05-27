package com.foody.manager;

import com.foody.dto.request.*;
import com.foody.dto.response.RemovePointFromRecipeRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(url = "http://localhost:9070/api/v1/recipe", name = "comment-recipe" ,decode404 = true)
public interface IRecipeManager {

    @GetMapping("/does-recipe-exist/{recipeId}")
    public ResponseEntity<Boolean> doesRecipeExist(@PathVariable String recipeId);

    @PutMapping("/add-comment-to-recipe")
    public ResponseEntity<Boolean> addCommentToRecipe(@RequestBody AddCommentToRecipeRequestDto dto);

    @DeleteMapping("/delete-comment-to-recipe")
    public ResponseEntity<Boolean> deleteCommentToRecipe(@RequestBody DeleteCommentToRecipeRequestDto dto);

    @DeleteMapping("/remove-deleted-user-comments-from-recipe")
    public ResponseEntity<Boolean> removeDeletedUserCommentsFromRecipe(@RequestBody DeleteDeletedUserCommentsFromRecipeRequestDto dto);

    @PostMapping("/add-point-id-to-recipe")
    public ResponseEntity<Boolean> addPointIdToRecipe(@RequestBody AddPointIdToRecipeRequestDto dto);

    @DeleteMapping("/remove-point-from-recipe")
    public ResponseEntity<Boolean> removePointFromRecipe(@RequestBody RemovePointFromRecipeRequestDto dto);

    @DeleteMapping("/remove-deleted-user-points-from-recipe")
    public ResponseEntity<Boolean> removeDeletedUserPointFromRecipe(@RequestBody DeleteDeletedUserPointsFromRecipeRequestDto build);
}
