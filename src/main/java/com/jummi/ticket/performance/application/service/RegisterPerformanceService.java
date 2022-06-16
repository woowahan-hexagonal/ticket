package com.jummi.ticket.performance.application.service;

import com.jummi.ticket.performance.application.port.in.RegisterPerformanceCommand;
import com.jummi.ticket.performance.application.port.in.RegisterPerformanceRequest;
import com.jummi.ticket.performance.application.port.out.SavePerformancePort;
import com.jummi.ticket.performance.converter.PerformanceMapper;
import com.jummi.ticket.performance.domain.Performance;
import com.jummi.ticket.performance.domain.PerformanceId;
import com.jummi.ticket.performance.domain.Series;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegisterPerformanceService implements RegisterPerformanceCommand {
    private final PerformanceMapper mapper;
    private final SavePerformancePort savePerformancePort;

    @Override
    public PerformanceId registerPerformance(RegisterPerformanceRequest request) {
        Performance performance = mapper.convertRequestToDomainEntity(request);
        List<Series> series = mapper.extractSeriesDomainEntity(request);
        Long performanceId = savePerformancePort.savePerformance(performance, series);

        return new PerformanceId(performanceId);
    }
}
