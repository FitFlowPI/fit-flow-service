package com.fitflow.fitflow_service.user.repository;

import com.fitflow.fitflow_service.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}