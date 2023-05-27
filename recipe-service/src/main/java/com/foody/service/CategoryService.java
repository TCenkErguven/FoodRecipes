package com.foody.service;

import com.foody.dto.request.SaveCategoryRequestDto;
import com.foody.dto.request.UpdateCategoryRequestDto;
import com.foody.dto.response.SaveCategoryResponseDto;
import com.foody.dto.response.UpdateCategoryResponseDto;
import com.foody.exception.ErrorType;
import com.foody.exception.RecipeAndCategoryManagerException;
import com.foody.mapper.ICategoryMapper;
import com.foody.repository.ICategoryRepository;
import com.foody.repository.entity.Category;
import com.foody.repository.entity.enums.ERole;
import com.foody.utility.JwtTokenProvider;
import com.foody.utility.ServiceManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService extends ServiceManager<Category,String> {
    private final ICategoryRepository categoryRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private CategoryService(ICategoryRepository categoryRepository,
                            JwtTokenProvider jwtTokenProvider){
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    public SaveCategoryResponseDto saveCategory(String token, SaveCategoryRequestDto dto){
        if(jwtTokenProvider.getRoleFromToken(token).get().equals(String.valueOf(ERole.ADMIN))){
            if(!categoryRepository.existsByCategoryNameIgnoreCase(dto.getCategoryName())){
                Category category = ICategoryMapper.INSTANCE.fromSaveCategoryRequestDtoToCategory(dto);
                save(category);
                return ICategoryMapper.INSTANCE.fromCategoryToSaveCategoryResponseDto(category);
            }
            throw new RecipeAndCategoryManagerException(ErrorType.CATEGORY_ALREADY_EXIST);
        }
        throw new RecipeAndCategoryManagerException(ErrorType.INVALID_TOKEN);
    }

    public Boolean deleteCategoryById(String token, String categoryId){
        if(jwtTokenProvider.getRoleFromToken(token).get().equals(String.valueOf(ERole.ADMIN))){
            Optional<Category> optionalCategory = findById(categoryId);
            if(optionalCategory.isPresent()){
                deleteById(categoryId);
                return true;
            }
            throw new RecipeAndCategoryManagerException(ErrorType.CATEGORY_NOT_FOUND);
        }
        throw new RecipeAndCategoryManagerException(ErrorType.INVALID_TOKEN);
    }

    public UpdateCategoryResponseDto updateCategory(String token, UpdateCategoryRequestDto dto){
        if(jwtTokenProvider.getRoleFromToken(token).get().equals(String.valueOf(ERole.ADMIN))){
            Optional<Category> optionalCategory = findById(dto.getId());
            if(optionalCategory.isPresent()){
                if(!categoryRepository.existsByCategoryNameIgnoreCase(dto.getCategoryName())){
                    update(ICategoryMapper.INSTANCE.fromUpdateCategoryRequestDtoToCategory(dto,optionalCategory.get()));
                    return ICategoryMapper.INSTANCE.fromCategoryToUpdateCategoryResponseDto(optionalCategory.get());
                }
                throw new RecipeAndCategoryManagerException(ErrorType.CATEGORY_ALREADY_EXIST);
            }
            throw new RecipeAndCategoryManagerException(ErrorType.CATEGORY_NOT_FOUND);
        }
        throw new RecipeAndCategoryManagerException(ErrorType.INVALID_TOKEN);
    }

    public boolean existsByCategoryId(String categoryId){
        return categoryRepository.existsById(categoryId);
    }
}
