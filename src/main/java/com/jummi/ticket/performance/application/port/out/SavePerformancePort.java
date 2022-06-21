package com.jummi.ticket.performance.application.port.out;

import com.jummi.ticket.performance.domain.Performance;
import com.jummi.ticket.performance.domain.Series;

import java.util.List;

public interface SavePerformancePort {
    Long savePerformance(Performance performance, List<Series> series);
}
