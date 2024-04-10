package com.example.logger.controller;

import com.example.logger.dto.AverageExecutionInfoDto;
import com.example.logger.dto.ExecutionInfoDto;
import com.example.logger.dto.MethodDto;
import com.example.logger.dto.PageResponse;
import com.example.logger.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/log/methods")
public class LoggingController {

    private final LoggingService loggingService;

    @GetMapping
    public PageResponse<MethodDto> findAllMethods(
            @ParameterObject @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        var allMethods = loggingService.findAllMethods(pageable);
        return PageResponse.of(allMethods);
    }

    @GetMapping("/{methodId}")
    public PageResponse<ExecutionInfoDto> findAllExecutions(
            @PathVariable("methodId") Integer methodId,
            @ParameterObject @PageableDefault(size = 10, sort = "executionTime") Pageable pageable) {
        var allExecutions = loggingService.findAllExecutions(methodId, pageable);
        return PageResponse.of(allExecutions);
    }

    @GetMapping("/{methodId}/avg")
    public AverageExecutionInfoDto getAverageInfo(@PathVariable("methodId") Integer methodId) {
        return loggingService.getAverageInfo(methodId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Логи для метода не найдены"));
    }
}
