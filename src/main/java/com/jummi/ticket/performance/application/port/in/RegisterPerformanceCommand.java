package com.jummi.ticket.performance.application.port.in;

import com.jummi.ticket.performance.adapter.web.RegisterPerformanceRequest;
import com.jummi.ticket.performance.domain.PerformanceId;

public interface RegisterPerformanceCommand {
    PerformanceId registerPerformance(RegisterPerformanceRequest request);
}
