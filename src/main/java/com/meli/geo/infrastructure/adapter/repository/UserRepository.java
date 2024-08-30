package com.meli.geo.infrastructure.adapter.repository;

import com.meli.geo.infrastructure.adapter.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByIpUser(String ipUser);
}
