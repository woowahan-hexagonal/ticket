package com.jummi.ticket.performance.domain;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record Series(Long seriesId, Long performanceId, LocalDate playDate, LocalTime playTime, List<String> casts,
                     boolean isPerformed) {
    @Builder
    public Series {
    }
}
