package com.foody.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(url = "http://localhost:9060/api/v1/comment", name = "recipe-comment" ,decode404 = true)
public interface ICommentManager {

    @DeleteMapping("/delete-recipe-comments")
    public ResponseEntity<Boolean> deleteRecipeComments(@RequestBody List<String> commentIds);

}
