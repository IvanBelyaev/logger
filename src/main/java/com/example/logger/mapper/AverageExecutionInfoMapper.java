package com.example.logger.mapper;

import com.example.logger.dto.AverageExecutionInfoDto;
import org.springframework.stereotype.Component;

@Component
public class AverageExecutionInfoMapper implements Mapper<Double, AverageExecutionInfoDto> {

    @Override
    public AverageExecutionInfoDto map(Double object) {
        return new AverageExecutionInfoDto(object);
    }
}
