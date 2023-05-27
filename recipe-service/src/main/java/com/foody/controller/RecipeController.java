package com.foody.controller;

import com.foody.dto.request.*;
import com.foody.dto.response.*;
import com.foody.repository.entity.Recipe;
import com.foody.service.RecipeService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.foody.constants.ApiUrls.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(RECIPE)
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping(SAVE + "/{token}")
    public ResponseEntity<SaveRecipeResponseDto> saveRecipe(@PathVariable String token, @RequestBody @Valid SaveRecipeRequestDto dto){
        return ResponseEntity.ok(recipeService.saveRecipe(token,dto));
    }

    @PutMapping(UPDATE + "/{token}")
    public ResponseEntity<UpdateRecipeResponseDto> updateRecipe(@PathVariable String token, @RequestBody @Valid UpdateRecipeRequestDto dto){
        return ResponseEntity.ok(recipeService.updateRecipe(token,dto));
    }

    @DeleteMapping(DELETE_RECIPE + "/{token}" + "/{recipeId}")
    public ResponseEntity<Boolean> deleteRecipe(@PathVariable String token, @PathVariable String recipeId){
        return ResponseEntity.ok(recipeService.deleteRecipe(token,recipeId));
    }

    @Hidden
    @GetMapping(DOES_RECIPE_EXIST +"/{recipeId}")
    public ResponseEntity<Boolean> doesRecipeExist(@PathVariable String recipeId){
        return ResponseEntity.ok(recipeService.doesRecipeExist(recipeId));
    }

    @Hidden
    @PutMapping(ADD_COMMENT_TO_RECIPE)
    public ResponseEntity<Boolean> addCommentToRecipe(@RequestBody AddCommentToRecipeResponseDto dto){
        return ResponseEntity.ok(recipeService.addCommentToRecipe(dto));
    }

    @Hidden
    @DeleteMapping(DELETE_COMMENT_TO_RECIPE)
    public ResponseEntity<Boolean> deleteCommentToRecipe(@RequestBody DeleteCommentToRecipeResponseDto dto){
        return ResponseEntity.ok(recipeService.deleteCommentToRecipe(dto));
    }

    @Hidden
    @DeleteMapping(REMOVE_DELETE_USER_COMMENTS_FROM_RECIPE)
    public ResponseEntity<Boolean> removeDeletedUserCommentsFromRecipe(@RequestBody DeleteDeletedUserCommentsFromRecipeResponseDto dto){
        return ResponseEntity.ok(recipeService.removeDeletedUserCommentsFromRecipe(dto));
    }

    @Hidden
    @PostMapping(ADD_POINT_ID_TO_RECIPE)
    public ResponseEntity<Boolean> addPointIdToRecipe(@RequestBody AddPointIdToRecipeResponseDto dto){
        return ResponseEntity.ok(recipeService.addPointIdToRecipe(dto));
    }

    @Hidden
    @DeleteMapping(REMOVE_POINT_FROM_RECIPE)
    public ResponseEntity<Boolean> removePointFromRecipe(@RequestBody RemovePointFromRecipeResponseDto dto){
        return ResponseEntity.ok(recipeService.removePointFromRecipe(dto));
    }

    @Hidden
    @DeleteMapping(REMOVE_DELETED_USER_POINTS_FROM_RECIPE)
    public ResponseEntity<Boolean> removeDeletedUserPointFromRecipe(@RequestBody DeleteDeletedUserPointsFromRecipeResponseDto dto){
        return ResponseEntity.ok(recipeService.removeDeletedUserPointFromRecipe(dto));
    }


    @GetMapping(ORDER_RECIPES_BY_CALORIES)
    public ResponseEntity<List<Recipe>> orderRecipesByCalories(){
        return ResponseEntity.ok(recipeService.orderRecipesWithCalories());
    }

    @GetMapping(SEARCH_FILTER_BY_CATEGORIES)
    public ResponseEntity<List<Recipe>> searchFilterByCategories(SearchByCategoriesRequestDto dto){
        return ResponseEntity.ok(recipeService.searchFilterWithCategories(dto));
    }

    @GetMapping(SEARCH_FILTER_BY_FOOD_NAME)
    public ResponseEntity<List<Recipe>> searchFilterByFoodName(String foodName){
        return ResponseEntity.ok(recipeService.searchFilterWithFoodName(foodName));
    }

    @GetMapping(SEARCH_FILTER_BY_INGREDIENT_NAMES)
    public ResponseEntity<Set<Recipe>> searchFilterByIngredientName(SearchByIngredientNamesRequestDto dto){
        return ResponseEntity.ok(recipeService.searchFilterWithIngredientName(dto));
    }

    @GetMapping("/findAllWithCache")
    public ResponseEntity<List<Recipe>> findAllWithCache(){
        return ResponseEntity.ok(recipeService.findAllWithCache());
    }


    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Recipe>> findAll(){
        return ResponseEntity.ok(recipeService.findAll());
    }

}
