package com.example.logger.mapper;

import com.example.logger.dto.ExecutionInfoDto;
import com.example.logger.entity.ExecutionInfo;
import org.springframework.stereotype.Component;

@Component
public class ExecutionInfoMapper implements Mapper<ExecutionInfo, ExecutionInfoDto> {
    @Override
    public ExecutionInfoDto map(ExecutionInfo object) {
        return new ExecutionInfoDto(object.getArgs(), object.getExecutionTime());
    }
}
