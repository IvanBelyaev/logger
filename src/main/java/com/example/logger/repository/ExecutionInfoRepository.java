package com.example.logger.repository;

import com.example.logger.entity.ExecutionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExecutionInfoRepository extends JpaRepository<ExecutionInfo, Long> {

    Page<ExecutionInfo> findAllByMethodId(Integer methodId, Pageable pageable);

    @Query("select avg(et.executionTime) from ExecutionInfo et where et.method.id = :methodId")
    Optional<Double> getAverageInfo(Integer methodId);
}
