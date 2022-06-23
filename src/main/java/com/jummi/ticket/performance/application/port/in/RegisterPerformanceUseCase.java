package com.jummi.ticket.performance.application.port.in;

import com.jummi.ticket.performance.adapter.web.RegisterPerformanceRequest;

public interface RegisterPerformanceUseCase {
    Long registerPerformance(RegisterPerformanceRequest request);
}
