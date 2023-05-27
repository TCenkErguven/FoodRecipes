package com.foody.repository;

import com.foody.repository.entity.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPointRepository extends MongoRepository<Point,String> {
    List<Point> findByUserProfileId(String userProfileId);
    Optional<Point> findByUserProfileIdAndRecipeId(String userProfileId, String recipeId);
}
