package com.foody.service;

import com.foody.dto.request.*;
import com.foody.dto.response.*;
import com.foody.exception.ErrorType;
import com.foody.exception.RecipeAndCategoryManagerException;
import com.foody.manager.ICommentManager;
import com.foody.manager.IUserManager;
import com.foody.mapper.IRecipeMapper;
import com.foody.repository.IRecipeRepository;
import com.foody.repository.entity.Category;
import com.foody.repository.entity.Ingredient;
import com.foody.repository.entity.Recipe;
import com.foody.repository.entity.enums.ERole;
import com.foody.utility.JwtTokenProvider;
import com.foody.utility.ServiceManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeService extends ServiceManager<Recipe,String> {
    private final IRecipeRepository recipeRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CategoryService categoryService;
    private final ICommentManager commentManager;
    private final IUserManager userManager;

    public RecipeService(IRecipeRepository recipeRepository,
                         JwtTokenProvider jwtTokenProvider,
                         CategoryService categoryService,
                         ICommentManager commentManager, IUserManager userManager){
        super(recipeRepository);
        this.recipeRepository = recipeRepository;
        this.categoryService = categoryService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.commentManager = commentManager;
        this.userManager = userManager;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "find-all-with-cache", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-calories", allEntries = true)
            },
            put ={
                    @CachePut(value = "order-recipes-with-food-name", key = "#dto.getFoodName().toLowerCase()"),
                    @CachePut(value = "search-filter-with-categories", key = "#dto.getCategoryIds()")
            }
    )
    public SaveRecipeResponseDto saveRecipe(String token, SaveRecipeRequestDto dto){
        if(!jwtTokenProvider.getRoleFromToken(token).get().equals(String.valueOf(ERole.ADMIN)))
            throw new RecipeAndCategoryManagerException(ErrorType.INVALID_TOKEN);
        Set<String> categoryNameSet = new HashSet<>();
        dto.getCategoryIds().forEach(categoryId -> {
            Optional<Category> optionalCategory = categoryService.findById(categoryId);
            if(optionalCategory.isEmpty())
                throw new RecipeAndCategoryManagerException(ErrorType.CATEGORY_NOT_FOUND);
            categoryNameSet.add(optionalCategory.get().getCategoryName());
        });
        Recipe recipe = IRecipeMapper.INSTANCE.fromSaveRecipeRequestDtoToRecipe(dto);
        save(recipe);
        List<Recipe> recipeList = findAll();
        if(!recipeList.isEmpty()){
            Set<String> recipeIdSet = new HashSet<>();
            recipe.getCategoryIds().forEach(categoryId -> {
                recipeList.forEach(recipe1->{
                    if(recipe1.getCategoryIds().contains(categoryId)){
                        recipeIdSet.add(recipe1.getId());
                    }
                });
            });
            userManager.checkUserFavoriteFoodsThenMail(FavoriteCategoryAlerterRequestDto.builder()
                    .foodName(recipe.getFoodName())
                    .recipeIds(recipeIdSet)
                    .categoryNames(categoryNameSet)
                    .build());
        }
        return IRecipeMapper.INSTANCE.fromRecipeToSaveRecipeResponseDto(recipe);
    }
    @Caching(
            evict = {
                    @CacheEvict(value = "find-all-with-cache", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-calories", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-food-name", allEntries = true),
                    @CacheEvict(value = "search-filter-with-categories", allEntries = true)
            }
    )
    public UpdateRecipeResponseDto updateRecipe(String token, UpdateRecipeRequestDto dto){
        if(!jwtTokenProvider.getRoleFromToken(token).get().equals(String.valueOf(ERole.ADMIN)))
            throw new RecipeAndCategoryManagerException(ErrorType.INVALID_TOKEN);
        Optional<Recipe> optionalRecipe = recipeRepository.findById(dto.getId());
        if(optionalRecipe.isEmpty())
            throw new RecipeAndCategoryManagerException(ErrorType.INVALID_TOKEN);
        IRecipeMapper.INSTANCE.fromUpdateRecipeRequestDtoToRecipe(dto,optionalRecipe.get());
        //Photos
        optionalRecipe.get().getImages().removeAll(dto.getRemoveImages());
        optionalRecipe.get().getImages().addAll(dto.getAddImages());
        //Types
        optionalRecipe.get().getTypes().removeAll(dto.getRemoveTypes());
        optionalRecipe.get().getTypes().addAll(dto.getAddTypes());
        //Category
        optionalRecipe.get().getCategoryIds().removeAll(dto.getRemoveCategoryIds());
        System.out.println(dto.getAddCategoryIds());
        dto.getAddCategoryIds().forEach(categoryId -> {
            if(!categoryService.existsByCategoryId(categoryId))
                throw new RecipeAndCategoryManagerException(ErrorType.CATEGORY_NOT_FOUND);
            if(optionalRecipe.get().getCategoryIds().contains(categoryId))
                throw new RecipeAndCategoryManagerException(ErrorType.CATEGORY_ALREADY_EXIST);
        });
        optionalRecipe.get().getCategoryIds().addAll(dto.getAddCategoryIds());
        //Ingredient
        optionalRecipe.get().getIngredientList().removeAll(dto.getRemoveIngredients());
        optionalRecipe.get().getIngredientList().addAll(dto.getAddIngredients());
        update(optionalRecipe.get());
        return IRecipeMapper.INSTANCE.fromRecipeToUpdateRecipeResponseDto(optionalRecipe.get());
    }
    @Caching(
            evict = {
                    @CacheEvict(value = "find-all-with-cache", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-calories", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-food-name", allEntries = true),
                    @CacheEvict(value = "search-filter-with-categories", allEntries = true),
            }
    )
    public Boolean deleteRecipe(String token,String recipeId){
        if(!jwtTokenProvider.getRoleFromToken(token).get().equals(String.valueOf(ERole.ADMIN)))
            throw new RecipeAndCategoryManagerException(ErrorType.INVALID_TOKEN);
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(optionalRecipe.isEmpty())
            throw new RecipeAndCategoryManagerException(ErrorType.RECIPE_NOT_FOUND);
        if(!optionalRecipe.get().getCommentIds().isEmpty())
            commentManager.deleteRecipeComments(optionalRecipe.get().getCommentIds());
        userManager.removeFavoriteUserRecipe(recipeId);
        deleteById(optionalRecipe.get().getId());
        return true;
    }

    public Boolean doesRecipeExist(String recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(optionalRecipe.isEmpty())
            return false;
        return true;
    }
    @Caching(
            evict = {
                    @CacheEvict(value = "find-all-with-cache", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-calories", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-food-name", allEntries = true),
                    @CacheEvict(value = "search-filter-with-categories", allEntries = true),
            }
    )
    public Boolean addCommentToRecipe(AddCommentToRecipeResponseDto dto) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(dto.getRecipeId());
        if(optionalRecipe.isEmpty())
            throw new RecipeAndCategoryManagerException(ErrorType.RECIPE_NOT_FOUND);
        optionalRecipe.get().getCommentIds().add(dto.getCommentId());
        update(optionalRecipe.get());
        return true;
    }
    @Caching(
            evict = {
                    @CacheEvict(value = "find-all-with-cache", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-calories", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-food-name", allEntries = true),
                    @CacheEvict(value = "search-filter-with-categories", allEntries = true),
            }
    )
    public Boolean deleteCommentToRecipe(DeleteCommentToRecipeResponseDto dto) {
        Optional<Recipe> optionalRecipe = findById(dto.getRecipeId());
        if(optionalRecipe.isEmpty())
            throw new RecipeAndCategoryManagerException(ErrorType.RECIPE_NOT_FOUND);
        optionalRecipe.get().getCommentIds().remove(dto.getCommentId());
        update(optionalRecipe.get());
        return true;
    }
    @Caching(
            evict = {
                    @CacheEvict(value = "find-all-with-cache", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-calories", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-food-name", allEntries = true),
                    @CacheEvict(value = "search-filter-with-categories", allEntries = true),
            }
    )
    public Boolean removeDeletedUserCommentsFromRecipe(DeleteDeletedUserCommentsFromRecipeResponseDto dto) {
        List<Recipe> recipeList = new ArrayList<>();
        dto.getDeletedCommentRecipeIds().forEach(recipeId -> {
            Optional<Recipe> recipe = findById(recipeId);
            if(recipe.isEmpty())
                throw new RecipeAndCategoryManagerException(ErrorType.RECIPE_NOT_FOUND);
            recipeList.add(recipe.get());
        });
        recipeList.forEach(recipe -> {
            recipe.getCommentIds().removeAll(dto.getDeletedCommentIds());
        });
        saveAll(recipeList);
        return true;
    }
    @Caching(
            evict = {
                    @CacheEvict(value = "find-all-with-cache", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-calories", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-food-name", allEntries = true),
                    @CacheEvict(value = "search-filter-with-categories", allEntries = true),
            }
    )
    public Boolean addPointIdToRecipe(AddPointIdToRecipeResponseDto dto) {
        Optional<Recipe> optionalRecipe = findById(dto.getRecipeId());
        if (optionalRecipe.isEmpty())
            throw new RecipeAndCategoryManagerException(ErrorType.RECIPE_NOT_FOUND);
        optionalRecipe.get().getPointIds().add(dto.getPointId());
        update(optionalRecipe.get());
        return true;
    }
    @Caching(
            evict = {
                    @CacheEvict(value = "find-all-with-cache", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-calories", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-food-name", allEntries = true),
                    @CacheEvict(value = "search-filter-with-categories", allEntries = true),
            }
    )
    public Boolean removePointFromRecipe(RemovePointFromRecipeResponseDto dto) {
        Optional<Recipe> optionalRecipe = findById(dto.getRecipeId());
        if(optionalRecipe.isEmpty())
            throw new RecipeAndCategoryManagerException(ErrorType.RECIPE_NOT_FOUND);
        optionalRecipe.get().getPointIds().remove(dto.getPointId());
        update(optionalRecipe.get());
        return true;
    }
    @Caching(
            evict = {
                    @CacheEvict(value = "find-all-with-cache", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-calories", allEntries = true),
                    @CacheEvict(value = "order-recipes-with-food-name", allEntries = true),
                    @CacheEvict(value = "search-filter-with-categories", allEntries = true),
            }
    )
    public Boolean removeDeletedUserPointFromRecipe(DeleteDeletedUserPointsFromRecipeResponseDto dto) {
        List<Recipe> recipeList = new ArrayList<>();
        dto.getDeletedPointRecipeIds().forEach(recipeId -> {
            Optional<Recipe> recipe = findById(recipeId);
            if(recipe.isEmpty())
                throw new RecipeAndCategoryManagerException(ErrorType.RECIPE_NOT_FOUND);
            recipeList.add(recipe.get());
        });
        recipeList.forEach(recipe -> {
            recipe.getPointIds().removeAll(dto.getDeletedPointIds());
        });
        saveAll(recipeList);
        return true;
    }


    @Cacheable(value = "search-filter-with-categories", key = "#dto.getCategoryIds()")
    public List<Recipe> searchFilterWithCategories(SearchByCategoriesRequestDto dto){
        List<Recipe> recipeList = recipeRepository.findAll();
        Set<Recipe> recipeSet = new HashSet<>();
            if (!dto.getCategoryIds().isEmpty()) {
                for (Recipe recipe : recipeList) {
                    for (String categoryId : dto.getCategoryIds()) {
                        if (!recipe.getCategoryIds().contains(categoryId)){
                            recipeSet.add(recipe);
                        }
                    }
                }
            }

        recipeList.removeAll(recipeSet);
        return recipeList;
    }
    @Cacheable(value = "order-recipes-with-food-name", key = "#foodName.toLowerCase()")
    public List<Recipe> searchFilterWithFoodName(String foodName){
        List<Recipe> recipeList = recipeRepository.findByFoodNameStartingWithIgnoreCase(foodName);
        if(recipeList.isEmpty())
            throw new RecipeAndCategoryManagerException(ErrorType.RECIPE_NOT_FOUND);
        return recipeList;
    }
    public Set<Recipe> searchFilterWithIngredientName(SearchByIngredientNamesRequestDto dto){
        List<Recipe> recipeList = recipeRepository.findAll();
        Set<Recipe> recipeSet = new HashSet<>();
        if(dto.getIngredientNames() != null){
            if(!dto.getIngredientNames().isEmpty()) {
                for (Recipe recipe : recipeList) {
                    for (Ingredient ingredient : recipe.getIngredientList()) {
                        if(dto.getIngredientNames().contains(ingredient.getIngredientName())){
                            recipeSet.add(recipe);

                        }
                    }
                }
            }
        }
        return recipeSet;
    }
    @Cacheable(value = "order-recipes-with-calories")
    public List<Recipe> orderRecipesWithCalories(){
        List<Recipe> recipeList = findAll();
        if(recipeList.isEmpty())
            throw new RecipeAndCategoryManagerException(ErrorType.RECIPE_NOT_FOUND);
        recipeList.sort(
                Comparator.comparing(Recipe::getNutritional,(n1,n2) -> {
                    return n1.getCalories().compareTo(n2.getCalories());
                })
        );
                return recipeList;
    }

    @Cacheable(value = "find-all-with-cache")
    public List<Recipe> findAllWithCache(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return findAll();
    }

    public Boolean deleteCategoryById(String token, String categoryId){
        if(jwtTokenProvider.getRoleFromToken(token).get().equals(String.valueOf(ERole.ADMIN))){
            Optional<Category> optionalCategory = categoryService.findById(categoryId);
            if(optionalCategory.isPresent()){
                List<Recipe> recipeList = findAll();
                recipeList.forEach(recipe -> {
                    if(recipe.getCategoryIds().size()==1 && recipe.getCategoryIds().get(0).equals(categoryId)){
                        throw new RecipeAndCategoryManagerException(ErrorType.BAD_REQUEST);
                    }
                });
                recipeList.forEach(recipe -> {
                    recipe.getCategoryIds().remove(categoryId);
                    update(recipe);
                });
                deleteById(categoryId);
                return true;
            }
            throw new RecipeAndCategoryManagerException(ErrorType.CATEGORY_NOT_FOUND);
        }
        throw new RecipeAndCategoryManagerException(ErrorType.INVALID_TOKEN);
    }

}
