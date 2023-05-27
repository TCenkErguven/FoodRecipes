package com.foody.service;

import com.foody.dto.request.AddPointRequestDto;
import com.foody.dto.request.RemovePointRequestDto;
import com.foody.dto.response.UserProfileResponseDto;
import com.foody.dto.response.AddPointResponseDto;
import com.foody.exception.CommentAndPointManagerException;
import com.foody.exception.ErrorType;
import com.foody.manager.IRecipeManager;
import com.foody.manager.IUserManager;
import com.foody.mapper.IPointMapper;
import com.foody.repository.IPointRepository;
import com.foody.repository.entity.Point;
import com.foody.repository.entity.enums.ERole;
import com.foody.utility.JwtTokenProvider;
import com.foody.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PointService extends ServiceManager<Point,String> {
    private final IPointRepository pointRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final IRecipeManager recipeManager;
    private final IUserManager userManager;
    public PointService(IPointRepository pointRepository,
                        JwtTokenProvider jwtTokenProvider,
                        IRecipeManager recipeManager,
                        IUserManager userManager){
        super(pointRepository);
        this.pointRepository = pointRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.recipeManager = recipeManager;
        this.userManager = userManager;
    }

    public List<Point> findByUserProfileId(String userProfileId){
        return pointRepository.findByUserProfileId(userProfileId);
    }

    public AddPointResponseDto addPoint(String token, AddPointRequestDto dto){

        Optional<Long> optionalAuthId = jwtTokenProvider.getIdFromToken(token);
        if (optionalAuthId.isEmpty())
            throw new CommentAndPointManagerException(ErrorType.USER_NOT_FOUND);
        UserProfileResponseDto userProfileRequestDto = userManager.getUserProfileDto(optionalAuthId.get()).getBody();
        if(recipeManager.doesRecipeExist(dto.getRecipeId()).getBody()){
            Optional<Point> optionalPoint = pointRepository.findByUserProfileIdAndRecipeId(userProfileRequestDto.getUserId(),dto.getRecipeId());
            if(optionalPoint.isPresent()){
                optionalPoint.get().setPoint(dto.getPoint());
                update(optionalPoint.get());
                return IPointMapper.INSTANCE.pointToAddPointResponseDto(optionalPoint.get());
            }else{
                Point point = IPointMapper.INSTANCE.fromAddPointRequestDtoToPoint(dto);
                point.setUserProfileId(userProfileRequestDto.getUserId());
                save(point);
                recipeManager.addPointIdToRecipe(IPointMapper.INSTANCE.pointToAddPointIdToRecipeRequestDto(point));
                return IPointMapper.INSTANCE.pointToAddPointResponseDto(point);
            }
        }
        throw new CommentAndPointManagerException(ErrorType.RECIPE_NOT_FOUND);
    }

    public Boolean removePoint(String token, RemovePointRequestDto dto){
        Optional<Long> optionalAuthId = jwtTokenProvider.getIdFromToken(token);
        if (optionalAuthId.isEmpty())
            throw new CommentAndPointManagerException(ErrorType.USER_NOT_FOUND);
        Optional<Point> optionalPoint = findById(dto.getPointId());
        if(optionalPoint.isEmpty())
            throw new CommentAndPointManagerException(ErrorType.POINT_NOT_FOUND);
        UserProfileResponseDto userProfileRequestDto = userManager.getUserProfileDto(optionalAuthId.get()).getBody();
        if(optionalPoint.get().getUserProfileId().equals(userProfileRequestDto.getUserId()) ||
                jwtTokenProvider.getRoleFromToken(token).equals(String.valueOf(ERole.ADMIN))) {
            deleteById(dto.getPointId());
            recipeManager.removePointFromRecipe(IPointMapper.INSTANCE.pointToRemovePointFromRecipe(optionalPoint.get()));
            return true;
        }
        throw new CommentAndPointManagerException(ErrorType.BAD_REQUEST);
    }
}
