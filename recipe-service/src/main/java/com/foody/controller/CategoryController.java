package com.foody.controller;

import com.foody.dto.request.SaveCategoryRequestDto;
import com.foody.dto.request.UpdateCategoryRequestDto;
import com.foody.dto.response.SaveCategoryResponseDto;
import com.foody.dto.response.UpdateCategoryResponseDto;
import com.foody.repository.entity.Category;
import com.foody.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.foody.constants.ApiUrls.*;

import java.util.List;


@RestController
@RequestMapping(CATEGORY)
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(SAVE + "/{token}")
    public ResponseEntity<SaveCategoryResponseDto> saveCategory(@PathVariable String token, @RequestBody SaveCategoryRequestDto dto){
        return ResponseEntity.ok(categoryService.saveCategory(token,dto));
    }

    @PutMapping(UPDATE + "/{token}")
    public ResponseEntity<UpdateCategoryResponseDto> saveCategory(@PathVariable String token, @RequestBody UpdateCategoryRequestDto dto){
        return ResponseEntity.ok(categoryService.updateCategory(token,dto));
    }



    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Category>> findAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }


}
