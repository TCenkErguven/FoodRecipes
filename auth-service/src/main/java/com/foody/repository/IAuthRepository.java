package com.foody.repository;

import com.foody.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByUsername(String username);
    Boolean existsByUsernameOrEmail(String username,String email);
    Optional<Auth> findByEmail(String email);
}
