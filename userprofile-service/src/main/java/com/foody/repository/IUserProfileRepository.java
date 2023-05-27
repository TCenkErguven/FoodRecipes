package com.foody.repository;

import com.foody.repository.entity.UserProfile;
import org.mapstruct.control.MappingControl;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface IUserProfileRepository extends MongoRepository<UserProfile,String> {
    Optional<UserProfile> findByAuthId(Long authId);
}
