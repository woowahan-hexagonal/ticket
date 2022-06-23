package com.jummi.ticket.performance.application.service;

import com.jummi.ticket.performance.adapter.web.RegisterPerformanceRequest;
import com.jummi.ticket.performance.application.port.out.SavePerformanceCommand;
import com.jummi.ticket.performance.converter.PerformanceMapper;
import com.jummi.ticket.performance.domain.Performance;
import com.jummi.ticket.performance.domain.Series;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterPerformanceServiceTest {

    @Mock
    private PerformanceMapper performanceMapper;

    @Mock
    private SavePerformanceCommand savePerformanceCommand;

    @InjectMocks
    private RegisterPerformanceService registerPerformanceService;

    @Test
    @DisplayName("register performance use case")
    void registerPerformance() {
        RegisterPerformanceRequest request = mock(RegisterPerformanceRequest.class);
        Performance performance = mock(Performance.class);
        List<Series> series = mock(ArrayList.class);

        given(performanceMapper.convertRequestToDomainEntity(request))
                .willReturn(performance);
        given(performanceMapper.extractSeriesDomainEntity(request))
                .willReturn(series);
        given(savePerformanceCommand.savePerformance(performance, series))
                .willReturn(anyLong());

        Long performanceId = registerPerformanceService.registerPerformance(request);

        assertThat(performanceId).isNotNull();
        verify(performanceMapper, times(1))
                .convertRequestToDomainEntity(request);
        verify(performanceMapper, times(1))
                .extractSeriesDomainEntity(request);
        verify(savePerformanceCommand, times(1))
                .savePerformance(performance, series);
    }
}
