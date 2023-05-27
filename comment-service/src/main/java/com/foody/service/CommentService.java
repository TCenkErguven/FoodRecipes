package com.foody.service;

import com.foody.dto.request.*;
import com.foody.dto.response.*;
import com.foody.exception.ErrorType;
import com.foody.exception.CommentAndPointManagerException;
import com.foody.manager.IRecipeManager;
import com.foody.manager.IUserManager;
import com.foody.mapper.ICommentMapper;
import com.foody.repository.ICommentRepository;
import com.foody.repository.entity.Comment;
import com.foody.repository.entity.Point;
import com.foody.repository.entity.enums.ERole;
import com.foody.utility.JwtTokenProvider;
import com.foody.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentService extends ServiceManager<Comment,String> {
    private final ICommentRepository commentRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final IUserManager userManager;
    private final IRecipeManager recipeManager;
    private final PointService pointService;
    public CommentService(ICommentRepository commentRepository,
                          JwtTokenProvider jwtTokenProvider,
                          IUserManager userManager,
                          IRecipeManager recipeManager,
                          PointService pointService){
        super(commentRepository);
        this.commentRepository = commentRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userManager = userManager;
        this.recipeManager = recipeManager;
        this.pointService = pointService;
    }
    public AddCommentResponseDto addComment(String token, AddCommentRequestDto dto){
        Optional<Long> optionalAuthId = jwtTokenProvider.getIdFromToken(token);
        if (optionalAuthId.isEmpty())
            throw new CommentAndPointManagerException(ErrorType.USER_NOT_FOUND);
        UserProfileResponseDto userNameResponseDto = userManager.getUserProfileDto(optionalAuthId.get()).getBody();
        Boolean status = recipeManager.doesRecipeExist(dto.getRecipeId()).getBody();
        System.out.println(status);
        if(!status)
            throw new CommentAndPointManagerException(ErrorType.RECIPE_NOT_FOUND);
        Comment comment = ICommentMapper.INSTANCE.fromAddCommentRequestDtoToComment(dto);
        comment.setUsername(userNameResponseDto.getUsername());
        comment.setUserProfileId(userNameResponseDto.getUserId());
        save(comment);
        System.out.println(comment);
        System.out.println(AddCommentToRecipeRequestDto.builder()
                .commentId(comment.getId())
                .recipeId(dto.getRecipeId())
                .build());
        recipeManager.addCommentToRecipe(AddCommentToRecipeRequestDto.builder()
                        .commentId(comment.getId())
                        .recipeId(dto.getRecipeId())
                        .build()
                );
        AddCommentResponseDto dtoResponse = AddCommentResponseDto.builder()
                .username(comment.getUsername())
                .comment(comment.getComment())
                .recipeId(comment.getRecipeId())
                .commentDate(new Date(comment.getCommentDate()))
                .build();
        return dtoResponse;
    }

    public Boolean deleteComment(String token, DeleteCommentRequestDto dto){
        Optional<Long> optionalAuthId = jwtTokenProvider.getIdFromToken(token);
        if (optionalAuthId.isEmpty())
            throw new CommentAndPointManagerException(ErrorType.USER_NOT_FOUND);
        Optional<Comment> optionalComment = commentRepository.findById(dto.getCommentId());
        if(optionalComment.isEmpty())
            throw new CommentAndPointManagerException(ErrorType.COMMENT_NOT_FOUND);
        Boolean status = recipeManager.doesRecipeExist(dto.getRecipeId()).getBody();
        if(!status)
            throw new CommentAndPointManagerException(ErrorType.RECIPE_NOT_FOUND);
        String username = userManager.getUserProfileDto(optionalAuthId.get()).getBody().getUsername();
        if(username.equals(optionalComment.get().getUsername()) || jwtTokenProvider.getRoleFromToken(token).equals(String.valueOf(ERole.ADMIN))){
            deleteById(dto.getCommentId());
            recipeManager.deleteCommentToRecipe(ICommentMapper.INSTANCE.fromDeleteCommentRequestDtoToDeleteCommentResponseDto(dto));
            return true;
        }
        throw new CommentAndPointManagerException(ErrorType.BAD_REQUEST);
    }

    public UpdateCommentResponseDto updateComment(String token, UpdateCommentRequestDto dto){
        Optional<Long> optionalAuthId = jwtTokenProvider.getIdFromToken(token);
        if (optionalAuthId.isEmpty())
            throw new CommentAndPointManagerException(ErrorType.USER_NOT_FOUND);
        Optional<Comment> optionalComment = commentRepository.findById(dto.getCommentId());
        if(optionalComment.isEmpty())
            throw new CommentAndPointManagerException(ErrorType.COMMENT_NOT_FOUND);
        Boolean status = recipeManager.doesRecipeExist(dto.getRecipeId()).getBody();
        if(!status)
            throw new CommentAndPointManagerException(ErrorType.RECIPE_NOT_FOUND);
        System.out.println(status);
        String username = userManager.getUserProfileDto(optionalAuthId.get()).getBody().getUsername();
        if(username.equals(optionalComment.get().getUsername())){
            optionalComment.get().setCommentDate(System.currentTimeMillis());
            update(ICommentMapper.INSTANCE.fromUpdateCommentRequestDtoToComment(dto,optionalComment.get()));
            return ICommentMapper.INSTANCE.fromCommentToUpdateCommentResponseDto(optionalComment.get());
        }
        throw new CommentAndPointManagerException(ErrorType.BAD_REQUEST);
    }

    public Boolean deleteRecipeComments(List<String> commentIds) {
       commentIds.forEach(commentId -> {
           deleteById(commentId);
       });
        return true;
    }

    public Boolean updateCommentUsername(UpdateCommentUsernameResponseDto dto) {
        List<Comment> commentList = commentRepository.findByUserProfileId(dto.getUserId());
        if(commentList.isEmpty())
            throw new CommentAndPointManagerException(ErrorType.COMMENT_NOT_FOUND);
        commentList.forEach(comment -> {
            comment.setUsername(dto.getNewUsername());
            update(comment);
        });
        return true;
    }

    public Boolean removeAllUserCommentsAndPoints(RemoveAllUserCommentsAndPointsResponseDto dto) {
        List<Comment> comments = commentRepository.findByUserProfileId(dto.getId());
        Set<String> deletedCommentRecipeIds = new HashSet<>();
        List<String> deletedCommentIds = new ArrayList<>();
        if(!comments.isEmpty()){
            comments.forEach(comment -> {
                deletedCommentRecipeIds.add(comment.getRecipeId());
                deletedCommentIds.add(comment.getId());
                deleteById(comment.getId());
            });
            recipeManager.removeDeletedUserCommentsFromRecipe(DeleteDeletedUserCommentsFromRecipeRequestDto
                    .builder()
                    .deletedCommentIds(deletedCommentIds)
                    .deletedCommentRecipeIds(deletedCommentRecipeIds)
                    .build()
            );
        }
        List<Point> points = pointService.findByUserProfileId(dto.getId());
        Set<String> deletedPointRecipeIds = new HashSet<>();
        List<String> deletedPointIds = new ArrayList<>();
        if(!points.isEmpty()){
            points.forEach(point -> {
                deletedPointRecipeIds.add(point.getRecipeId());
                deletedPointIds.add(point.getId());
                pointService.deleteById(point.getId());
            });
            recipeManager.removeDeletedUserPointFromRecipe(DeleteDeletedUserPointsFromRecipeRequestDto.builder()
                            .deletedPointIds(deletedPointIds)
                            .deletedPointRecipeIds(deletedPointRecipeIds)
                    .build()
            );
        }
        return true;
    }
}
