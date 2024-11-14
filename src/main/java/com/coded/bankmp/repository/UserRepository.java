package com.coded.bankmp.repository;

import com.coded.bankmp.entity.UserEntity;
import com.coded.bankmp.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}
