package com.example.logger.service;

import com.example.logger.dto.AverageExecutionInfoDto;
import com.example.logger.dto.ExecutionInfoDto;
import com.example.logger.dto.MethodDto;
import com.example.logger.entity.ExecutionInfo;
import com.example.logger.entity.Method;
import com.example.logger.mapper.AverageExecutionInfoMapper;
import com.example.logger.mapper.ExecutionInfoMapper;
import com.example.logger.mapper.MethodMapper;
import com.example.logger.repository.ExecutionInfoRepository;
import com.example.logger.repository.MethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LoggingService {

    private final MethodRepository methodRepository;

    private final ExecutionInfoRepository executionInfoRepository;

    private final MethodMapper methodMapper;

    private final ExecutionInfoMapper executionInfoMapper;

    private final AverageExecutionInfoMapper averageExecutionInfoMapper;

    @Async
    @Transactional
    public void addLog(String methodName, String args, long executionTime) {
        var method = methodRepository.findByName(methodName)
                .orElseGet(() -> {
                    var newMethod = Method.builder()
                            .name(methodName)
                            .build();
                    methodRepository.save(newMethod);
                    methodRepository.flush();
                    return newMethod;
                });
        var executionInfo = ExecutionInfo.builder()
                .method(method)
                .args(args)
                .executionTime(executionTime)
                .build();
        executionInfoRepository.save(executionInfo);
    }

    public Page<MethodDto> findAllMethods(Pageable pageable) {
        return methodRepository.findAll(pageable)
                .map(methodMapper::map);
    }

    public Page<ExecutionInfoDto> findAllExecutions(Integer methodId, Pageable pageable) {
        return executionInfoRepository.findAllByMethodId(methodId, pageable)
                .map(executionInfoMapper::map);
    }

    public Optional<AverageExecutionInfoDto> getAverageInfo(Integer methodId) {
        return executionInfoRepository.getAverageInfo(methodId)
                .flatMap(d -> Optional.of(averageExecutionInfoMapper.map(d)));
    }
}
