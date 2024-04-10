package com.example.logger.repository;

import com.example.logger.entity.Method;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MethodRepository extends JpaRepository<Method, Integer> {

    Optional<Method> findByName(String methodName);
}
