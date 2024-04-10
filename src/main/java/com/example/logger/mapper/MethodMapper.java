package com.example.logger.mapper;

import com.example.logger.dto.MethodDto;
import com.example.logger.entity.Method;
import org.springframework.stereotype.Component;

@Component
public class MethodMapper implements Mapper<Method, MethodDto> {

    @Override
    public MethodDto map(Method object) {
        return new MethodDto(object.getId(), object.getName());
    }
}
