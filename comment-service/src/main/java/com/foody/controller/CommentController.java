package com.foody.controller;

import com.foody.dto.request.AddCommentRequestDto;
import com.foody.dto.request.DeleteCommentRequestDto;
import com.foody.dto.request.UpdateCommentRequestDto;
import com.foody.dto.response.AddCommentResponseDto;
import com.foody.dto.response.RemoveAllUserCommentsAndPointsResponseDto;
import com.foody.dto.response.UpdateCommentResponseDto;
import com.foody.dto.response.UpdateCommentUsernameResponseDto;
import com.foody.repository.entity.Comment;
import com.foody.service.CommentService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.foody.constants.ApiUrls.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(COMMENT)
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping(ADD_COMMENT +"/{token}")
    public ResponseEntity<AddCommentResponseDto> addComment(@PathVariable String token, @RequestBody @Valid AddCommentRequestDto dto){
        return ResponseEntity.ok(commentService.addComment(token,dto));
    }

    @DeleteMapping(DELETE_COMMENT + "/{token}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable String token, @RequestBody DeleteCommentRequestDto dto){
        return ResponseEntity.ok(commentService.deleteComment(token,dto));
    }

    @PutMapping(UPDATE_COMMENT + "/{token}")
    public ResponseEntity<UpdateCommentResponseDto> updateComment(@PathVariable String token, @RequestBody UpdateCommentRequestDto dto){
        return ResponseEntity.ok(commentService.updateComment(token,dto));
    }

    @Hidden
    @DeleteMapping(DELETE_RECIPE_COMMENT)
    public ResponseEntity<Boolean> deleteRecipeComments(@RequestBody List<String> commentIds){
        return ResponseEntity.ok(commentService.deleteRecipeComments(commentIds));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Comment>> findAll(){
        return ResponseEntity.ok(commentService.findAll());
    }

    @Hidden
    @PutMapping(UPDATE_COMMENT_USERNAME)
    public ResponseEntity<Boolean> updateCommentUsername(@RequestBody UpdateCommentUsernameResponseDto dto){
        return ResponseEntity.ok(commentService.updateCommentUsername(dto));
    }

    @Hidden
    @DeleteMapping(REMOVE_ALL_USER_COMMENTS_AND_POINTS)
    public ResponseEntity<Boolean> removeAllUserCommentsAndPoints(@RequestBody RemoveAllUserCommentsAndPointsResponseDto dto){
        return ResponseEntity.ok(commentService.removeAllUserCommentsAndPoints(dto));
    }



}
