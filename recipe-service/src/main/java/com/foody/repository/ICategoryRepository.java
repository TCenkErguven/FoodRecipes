package com.foody.repository;

import com.foody.repository.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends MongoRepository<Category,String> {
    Boolean existsByCategoryNameIgnoreCase(String categoryName);
    boolean existsById(String categoryId);
}
