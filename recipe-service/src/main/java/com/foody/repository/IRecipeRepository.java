package com.foody.repository;

import com.foody.repository.entity.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRecipeRepository extends MongoRepository<Recipe,String> {
    List<Recipe> findByFoodNameNotContainsIgnoreCase(String foodName);

    List<Recipe> findByFoodNameStartingWithIgnoreCase(String foodName);
}
