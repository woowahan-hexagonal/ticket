package com.jummi.ticket.common.converter;

import com.jummi.ticket.performance.converter.PerformanceMapper;
import org.mapstruct.factory.Mappers;

public class Converters {
    public static final PerformanceMapper PERFORMANCE_MAPPER = Mappers.getMapper(PerformanceMapper.class);
}
