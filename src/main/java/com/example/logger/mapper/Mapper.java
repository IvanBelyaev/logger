package com.example.logger.mapper;

public interface Mapper<F, T> {
    T map(F object);
}
